package com.indisp.harrypottertrivia.search.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.indisp.harrypottertrivia.search.data.local.dao.BookDao
import com.indisp.harrypottertrivia.search.data.local.dao.CharacterDao
import com.indisp.harrypottertrivia.search.data.local.dao.HouseDao
import com.indisp.harrypottertrivia.search.data.local.dao.SpellDao
import com.indisp.harrypottertrivia.search.data.local.entity.BookEntity
import com.indisp.harrypottertrivia.search.data.local.entity.CharacterEntity
import com.indisp.harrypottertrivia.search.data.local.entity.HouseEntity
import com.indisp.harrypottertrivia.search.data.local.entity.SpellEntity

@Database(entities = [BookEntity::class, CharacterEntity::class, HouseEntity::class, SpellEntity::class], version = 1)
abstract class MainDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun characterDao(): CharacterDao
    abstract fun houseDao(): HouseDao
    abstract fun spellDao(): SpellDao
}