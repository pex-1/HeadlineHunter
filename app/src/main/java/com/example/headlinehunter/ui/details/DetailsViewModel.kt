package com.example.headlinehunter.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headlinehunter.core.domain.RssFeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: RssFeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()


    fun getArticleById(articleId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(article = repository.getArticleById(articleId))
            }
            repository.updateArticle(state.value.article.copy(isArticleRead = true))
        }
    }

    fun onAction(action: DetailsAction) {
        when (action) {

            else -> {}
        }
    }
}