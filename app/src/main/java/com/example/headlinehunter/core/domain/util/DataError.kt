package com.example.headlinehunter.core.domain.util

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        RSS_PARSING_EXCEPTION,
        NO_INTERNET,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        ALREADY_SUBSCRIBED
    }
}