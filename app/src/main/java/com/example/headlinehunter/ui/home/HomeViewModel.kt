package com.example.headlinehunter.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headlinehunter.core.domain.RssFeedRepository
import com.example.headlinehunter.core.domain.channel.Channel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RssFeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        repository.getCollapseChannels().onEach { collapsed ->
            _state.update {
                it.copy(collapseFeedSelection = collapsed)
            }
        }.launchIn(viewModelScope)

        repository.getChannels().onEach { channels ->
            _state.update {
                it.copy(channels = channels)
            }
            refreshArticles(channels)

        }.launchIn(viewModelScope)

        state
            .distinctUntilChangedBy { it.channels }
            .map { state ->
                val selectedChannels = state.channels.filter { channel -> channel.isSelected }
                if (selectedChannels.isNotEmpty()) {
                    repository.getArticles(selectedChannels.map { channel -> channel.id })
                        .onEach { article ->
                            if (article.isNotEmpty()) {
                                _state.update {
                                    it.copy(articles = article)
                                }
                            }
                        }.launchIn(viewModelScope)
                }
                else {
                    _state.update {
                        it.copy(articles = emptyList())
                    }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun refreshArticles(channels: List<Channel>) {
        val jobs = arrayListOf<Job>()
        channels.forEach {
            val job = viewModelScope.launch {
                repository.fetchArticles(it.link, it.id)
            }
            jobs.add(job)
        }
        jobs.joinAll()
        _state.update {
            it.copy(isRefreshing = false, isLoadingData = false)
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnChannelClick -> {
                viewModelScope.launch {
                    repository.updateChannel(action.channel)
                    if (action.channel.isSelected) {
                        repository.fetchArticles(action.channel.link, action.channel.id)
                    }
                }
            }

            is HomeAction.OnAddToFavoritesClick -> {
                viewModelScope.launch {
                    repository.updateArticle(
                        action.article.copy(
                            isFavorite = action.article.isFavorite.not()
                        )
                    )
                }
            }

            is HomeAction.OnPullToRefresh -> {
                _state.update {
                    it.copy(isRefreshing = true)
                }
                viewModelScope.launch {
                    val channelId = state.value.articles[0].channelId
                    val channel = state.value.channels.find { it.id == channelId }
                    channel?.let {
                        refreshArticles(listOf(it))
                    }
                }
            }

            is HomeAction.OnCollapseChannelsClick -> {
                val channelsCollapsed = _state.value.collapseFeedSelection.not()
                viewModelScope.launch {
                    repository.setCollapseChannels(channelsCollapsed)
                }
            }

            else -> {
            }
        }
    }
}