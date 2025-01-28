package com.example.headlinehunter.ui.unread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headlinehunter.core.domain.RssFeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UnreadViewModel(
    private val repository: RssFeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UnreadState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getUnreadArticles().collectLatest { articles ->
                _state.update {
                    it.copy(articles = articles)
                }
            }
        }
    }

    fun onAction(action: UnreadAction) {
        when (action) {
            is UnreadAction.OnFavoritesClick -> {
                viewModelScope.launch {
                    repository.updateArticle(action.article.copy(isFavorite = action.article.isFavorite.not()))
                }
            }

            else -> {}
        }
    }
}