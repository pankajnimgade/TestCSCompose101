package com.example.csvcompose101.data.models

data class Item(
    val author: String = "",
    val author_id: String = "",
    val date_taken: String = "",
    val description: String = "",
    val link: String = "",
    val media: Media? = null,
    val published: String = "",
    val tags: String = "",
    val title: String = ""
)