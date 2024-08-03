package com.indisp.harrypottertrivia.search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val fullName: String?,
    val nickName: String?,
    val actorName: String?,
    val imageUrl: String?,
    val dateOfBirth: String?,
    val house: String?,
)