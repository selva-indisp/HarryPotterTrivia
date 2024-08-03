package com.indisp.harrypottertrivia.search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpellEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val use: String?,
    val isLastShown: Boolean?
)
