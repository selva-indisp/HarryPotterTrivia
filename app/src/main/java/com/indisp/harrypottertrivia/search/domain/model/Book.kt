package com.indisp.harrypottertrivia.search.domain.model

data class Book(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val pages: Int,
    val coverImageUrl: String
): Catalog("Book")