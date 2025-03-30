package com.example.headlinehunter.core.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.headlinehunter.R

val HomeIcon: ImageVector
    get() = Icons.Filled.Home

val UnreadIcon: ImageVector
    get() = Icons.Filled.Newspaper

val BookmarkIcon: ImageVector
    get() = Icons.Filled.Bookmark

val BookmarkOutlineIcon: ImageVector
    get() = Icons.Filled.BookmarkBorder

val SettingsIcon: ImageVector
    get() = Icons.Filled.Person

val NotificationIcon: ImageVector
    get() = Icons.Filled.Notifications

val DarkModeIcon: ImageVector
    get() = Icons.Filled.DarkMode

val BackArrowIcon: ImageVector
    get() = Icons.Default.ArrowBackIosNew

val AddIcon: ImageVector
    get() = Icons.Default.Add

val RssIcon: ImageVector
    get() = Icons.Default.RssFeed

val CloseIcon: ImageVector
    get() = Icons.Default.Close

val ClipboardIcon: ImageVector
    get() = Icons.Default.ContentPaste

val ArrowForwardIcon: ImageVector
    get() = Icons.Default.ArrowForward

val DeleteIcon: ImageVector
    get() = Icons.Filled.Delete

val CollapseIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.collapse)
