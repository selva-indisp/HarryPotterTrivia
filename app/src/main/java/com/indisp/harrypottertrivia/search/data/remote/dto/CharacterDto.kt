package com.indisp.harrypottertrivia.search.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    @SerialName("index")
    val id: Int,
    val fullName: String,
    @SerialName("nickname")
    val nickName: String,
    @SerialName("interpretedBy")
    val actorName: String,
    @SerialName("children")
    val childrenNames: List<String>,
    @SerialName("image")
    val imageUrl: String,
    @SerialName("birthdate")
    val dateOfBirth: String,
    @SerialName("hogwartsHouse")
    val house: String,
)