package com.indisp.harrypottertrivia.search.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.indisp.harrypottertrivia.search.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Query("SELECT * FROM bookentity")
    suspend fun getAll(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<BookEntity>)

    @Query("SELECT * FROM bookentity WHERE (LOWER(title) LIKE '%' || LOWER(:name) || '%')")
    suspend fun search(name: String): List<BookEntity>
}