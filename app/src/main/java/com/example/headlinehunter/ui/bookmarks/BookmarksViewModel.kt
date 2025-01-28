package com.example.headlinehunter.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headlinehunter.core.domain.RssFeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val repository: RssFeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteArticles().collectLatest { articles ->
                _state.update {
                    it.copy(favoriteArticles = articles)
                }
            }
        }
    }

    fun onAction(action: BookmarksAction) {
        when (action) {
            is BookmarksAction.OnRemoveFromFavoritesClick -> {
                viewModelScope.launch {
                    if (repository.getChannelById(action.article.channelId) != null) {
                        repository.updateArticle(action.article.copy(isFavorite = false))
                    } else {
                        repository.removeArticle(action.article)
                    }
                }
            }

            else -> {
            }
        }
    }
}