package com.indisp.harrypottertrivia.search.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HouseDto(
    @SerialName("index")
    val id: Int?,
    @SerialName("house")
    val name: String?,
    val founder: String?,
    val animal: String?,
    val colors: List<String>?
)