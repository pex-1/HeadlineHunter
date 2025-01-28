package com.example.headlinehunter.database.mappers

import com.example.headlinehunter.core.domain.channel.Channel
import com.example.headlinehunter.database.entity.ChannelEntity

fun ChannelEntity.toChannel(): Channel {
    return Channel(
        id = id,
        title = name,
        description = description,
        link = link,
        image = image,
        lastUpdateDate = lastUpdatedDate,
        isSelected = isSelected,
        notificationsEnabled = notificationsEnabled
    )
}

fun Channel.toChannelEntity(): ChannelEntity {
    return ChannelEntity(
        id = id,
        name = title,
        description = description,
        link = link,
        image = image,
        lastUpdatedDate = lastUpdateDate,
        isSelected = isSelected,
        notificationsEnabled = notificationsEnabled
    )
}