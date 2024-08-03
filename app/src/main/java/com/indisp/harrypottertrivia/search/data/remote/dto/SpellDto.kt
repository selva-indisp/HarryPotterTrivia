package com.indisp.harrypottertrivia.search.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpellDto(
    @SerialName("index")
    val id: Int?,
    @SerialName("spell")
    val name: String?,
    val use: String?
)
