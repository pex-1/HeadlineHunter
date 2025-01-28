package com.example.headlinehunter.ui.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headlinehunter.core.domain.RssFeedRepository
import com.example.headlinehunter.core.domain.util.DataError
import com.example.headlinehunter.core.domain.util.onError
import com.example.headlinehunter.core.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsFeedViewModel(
    private val repository: RssFeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewsFeedState())
    val state = _state.asStateFlow()

    init {
        repository.getChannels().onEach { channels ->
            _state.update {
                it.copy(channels = channels)
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: NewsFeedAction) {
        when (action) {
            is NewsFeedAction.OnValueChanged -> {
                _state.update {
                    it.copy(feedLink = action.feedLink, linkError = null)
                }
            }

            is NewsFeedAction.OnShowNewFeedDialog -> {
                if (action.showNewFeedDialog) {
                    _state.update {
                        it.copy(showNewFeedDialog = true)
                    }
                } else {
                    dialogReset()
                }
            }

            is NewsFeedAction.OnNewFeedDialogSearch -> {
                viewModelScope.launch {
                    if (repository.channelExists(state.value.feedLink)) {
                        _state.update {
                            it.copy(linkError = DataError.Local.ALREADY_SUBSCRIBED)
                        }
                    } else {
                        repository.fetchRssChannel(state.value.feedLink)
                            .onSuccess { channel ->
                                _state.update {
                                    it.copy(channel = channel)
                                }
                            }.onError { errorMessage ->
                                _state.update {
                                    it.copy(linkError = errorMessage)
                                }
                            }
                    }
                }
            }

            is NewsFeedAction.OnNewFeedDialogSubscribe -> {
                viewModelScope.launch {
                    state.value.channel?.let { repository.addChannel(it) }
                        ?.onSuccess {
                            dialogReset()
                        }
                        ?.onError {
                            Timber.e("database error!")
                        }
                }
            }

            is NewsFeedAction.OnShowConfirmDeleteDialog -> {
                _state.update {
                    it.copy(
                        channel = action.channel,
                        showConfirmDeleteDialog = action.channel != null
                    )
                }
            }

            is NewsFeedAction.OnDeleteChannel -> {
                viewModelScope.launch {
                    state.value.channel?.id?.let { repository.removeChannel(it) }
                    _state.update {
                        it.copy(showConfirmDeleteDialog = false, channel = null)
                    }
                }
            }

            is NewsFeedAction.OnSubscribeClick -> {
                viewModelScope.launch {
                    repository.updateChannel(
                        channel = action.channel.copy(
                            notificationsEnabled =
                            action.channel.notificationsEnabled.not()
                        )
                    )
                }
            }

            is NewsFeedAction.OnShowGoToSettingsDialog -> {
                _state.update {
                    it.copy(
                        showNotificationSettingsDialog =
                        state.value.showNotificationSettingsDialog.not()
                    )
                }
            }

            else -> {}
        }
    }

    private fun dialogReset() {
        _state.update {
            it.copy(showNewFeedDialog = false, feedLink = "", linkError = null, channel = null)
        }
    }
}

