package io.keepcoding.filmica

data class Film(
        val title: String = "No title",
        val genre: String = "No genre",
        val rating: Float = 0.0f,
        val overview: String = "No overview",
        val date: String = "1999-09-19"
)