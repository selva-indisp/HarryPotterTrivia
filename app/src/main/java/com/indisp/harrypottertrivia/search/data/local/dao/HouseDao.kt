package com.indisp.harrypottertrivia.search.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.indisp.harrypottertrivia.search.data.local.entity.HouseEntity
import com.indisp.harrypottertrivia.search.data.local.entity.SpellEntity

@Dao
interface HouseDao {
    @Query("SELECT * FROM houseentity")
    suspend fun getAll(): List<HouseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<HouseEntity>)

    @Query("SELECT * FROM houseentity WHERE (LOWER(name) LIKE '%' || LOWER(:name) || '%')")
    suspend fun search(name: String): List<HouseEntity>
}