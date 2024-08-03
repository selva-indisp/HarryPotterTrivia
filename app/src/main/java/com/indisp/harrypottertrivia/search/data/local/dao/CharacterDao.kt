package com.indisp.harrypottertrivia.search.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.indisp.harrypottertrivia.search.data.local.entity.CharacterEntity
import com.indisp.harrypottertrivia.search.data.local.entity.SpellEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characterentity")
    suspend fun getAll(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<CharacterEntity>)

    @Query("SELECT * FROM characterentity WHERE (LOWER(fullName) LIKE '%' || LOWER(:name) || '%')")
    suspend fun search(name: String): List<CharacterEntity>
}