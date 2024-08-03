package com.indisp.harrypottertrivia.search.domain.model

data class Character(
    val id: Int,
    val fullName: String,
    val nickName: String,
    val actorName: String,
    val imageUrl: String,
    val dateOfBirth: String,
    val house: String,
): Catalog("Character")