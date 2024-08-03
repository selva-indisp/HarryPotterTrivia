package com.indisp.harrypottertrivia.search.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    @SerialName("number")
    val id: Int?,
    val title: String?,
    val description: String?,
    val releaseDate: String?,
    val pages: Int?,
    @SerialName("cover")
    val coverImageUrl: String?
)