package com.indisp.harrypottertrivia.search.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val description: String?,
    @ColumnInfo("release_date")
    val releaseDate: String?,
    val pages: Int?,
    @ColumnInfo("image")
    val coverImageUrl: String?
)