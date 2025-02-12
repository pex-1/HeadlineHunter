package com.example.headlinehunter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headlinehunter.core.domain.RssFeedRepository
import com.example.headlinehunter.core.domain.SyncFeedScheduler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RssFeedRepository,
    private val syncFeedScheduler: SyncFeedScheduler
) : ViewModel() {

    val theme: StateFlow<Boolean> = repository.getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    init {
        viewModelScope.launch {
            repository.notificationsEnabled().collectLatest { enabled ->
                if (enabled) {
                    syncFeedScheduler.scheduleSyncFeedWorker(15L)
                } else {
                    syncFeedScheduler.cancelSyncFeedWorker()
                }
            }
        }
    }

}