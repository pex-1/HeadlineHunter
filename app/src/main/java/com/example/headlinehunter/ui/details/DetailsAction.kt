package com.example.headlinehunter.ui.details

sealed interface DetailsAction {
    data object OnBackClick : DetailsAction
}