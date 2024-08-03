package com.indisp.harrypottertrivia.search.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.indisp.harrypottertrivia.search.data.local.entity.SpellEntity

@Dao
interface SpellDao {
    @Query("SELECT * FROM spellentity")
    suspend fun getAll(): List<SpellEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<SpellEntity>)

    @Query("SELECT * FROM spellentity WHERE (LOWER(name) LIKE '%' || LOWER(:name) || '%')")
    suspend fun search(name: String): List<SpellEntity>
}