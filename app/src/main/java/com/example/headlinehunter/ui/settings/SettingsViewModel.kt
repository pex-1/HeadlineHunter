package com.example.headlinehunter.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headlinehunter.core.domain.RssFeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: RssFeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        repository.getTheme().onEach { darkModeEnabled ->
            _state.update {
                it.copy(darkModeEnabled = darkModeEnabled)
            }
        }.launchIn(viewModelScope)

        repository.notificationsEnabled().onEach { notificationsEnabled ->
            _state.update {
                it.copy(notificationsEnabled = notificationsEnabled)
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnEnableNotifications -> {
                viewModelScope.launch {
                    repository.updateNotifications(state.value.notificationsEnabled.not())
                }
            }

            is SettingsAction.OnEnableDarkMode -> {
                viewModelScope.launch {
                    repository.setTheme(state.value.darkModeEnabled.not())
                }
            }

            is SettingsAction.OnShowGoToSettingsDialog -> {
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
}