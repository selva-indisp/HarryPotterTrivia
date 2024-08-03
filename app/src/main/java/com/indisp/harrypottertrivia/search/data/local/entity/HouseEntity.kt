package com.indisp.harrypottertrivia.search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HouseEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val founder: String?,
    val animal: String?
)