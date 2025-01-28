package com.example.headlinehunter.core.domain.article

data class Article(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val link: String = "",
    val pubDate: Long = 0,
    val image: String = "",
    val description: String = "",
    val channelId: Int = 0,
    val isFavorite: Boolean = false,
    val isArticleRead: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Article) return false

        if (title != other.title) return false
        if (pubDate != other.pubDate) return false
        if (isFavorite != other.isFavorite) return false
        if (image != other.image) return false
        if (description != other.description) return false
        if (author != other.author) return false
        if (isArticleRead != other.isArticleRead) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + pubDate.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }


}
