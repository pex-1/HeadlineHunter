package com.example.headlinehunter.core.domain.channel

data class Channel(
    val id: Int = 0,
    val title: String = "",
    val link: String = "",
    val description: String = "",
    val image: String = "",
    val lastUpdateDate: String = "",
    val isSelected: Boolean = false,
    val notificationsEnabled: Boolean = false
)
