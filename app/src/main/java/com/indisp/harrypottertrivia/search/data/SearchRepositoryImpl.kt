package com.indisp.harrypottertrivia.search.data

import android.util.Log
import com.indisp.core.Result
import com.indisp.harrypottertrivia.search.data.local.dao.BookDao
import com.indisp.harrypottertrivia.search.data.local.dao.CharacterDao
import com.indisp.harrypottertrivia.search.data.local.dao.HouseDao
import com.indisp.harrypottertrivia.search.data.local.dao.SpellDao
import com.indisp.harrypottertrivia.search.data.mapper.DtoToEntityMapper
import com.indisp.harrypottertrivia.search.data.mapper.EntityToDomainMapper
import com.indisp.harrypottertrivia.search.data.remote.SearchApiService
import com.indisp.harrypottertrivia.search.domain.model.Book
import com.indisp.harrypottertrivia.search.domain.model.Character
import com.indisp.harrypottertrivia.search.domain.model.House
import com.indisp.harrypottertrivia.search.domain.model.Spell
import com.indisp.harrypottertrivia.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val apiService: SearchApiService,
    private val bookDao: BookDao,
    private val characterDao: CharacterDao,
    private val houseDao: HouseDao,
    private val spellDao: SpellDao,
    private val entityToDomainMapper: EntityToDomainMapper,
    private val dtoToEntityMapper: DtoToEntityMapper
): SearchRepository {

    override suspend fun searchBook(query: String): List<Book> {
        return when(val response = apiService.searchBook(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                bookDao.search(query).map(entityToDomainMapper::toBook)
            }
            is Result.Success -> {
                val books = response.data.map(dtoToEntityMapper::toBook)
                bookDao.insertAll(books)
                val cachedData = bookDao.search(query)
                cachedData.map(entityToDomainMapper::toBook)
            }
        }
    }

    override suspend fun searchCharacter(query: String): List<Character> {
        return when(val response = apiService.searchCharacter(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                characterDao.search(query).map(entityToDomainMapper::toCharacter)
            }
            is Result.Success -> {
                val characters = response.data.map(dtoToEntityMapper::toCharacter)
                characterDao.insertAll(characters)
                val cachedData = characterDao.search(query)
                cachedData.map(entityToDomainMapper::toCharacter)
            }
        }
    }

    override suspend fun searchHouse(query: String): List<House> {
        return when(val response = apiService.searchHouse(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                houseDao.search(query).map(entityToDomainMapper::toHouse)
            }
            is Result.Success -> {
                val house = response.data.map(dtoToEntityMapper::toHouse)
                houseDao.insertAll(house)
                val cachedData = houseDao.search(query)
                cachedData.map(entityToDomainMapper::toHouse)
            }
        }
    }

    override suspend fun searchSpell(query: String): List<Spell> {
        return when(val response = apiService.searchSpell(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                spellDao.search(query).map(entityToDomainMapper::toSpell)
            }
            is Result.Success -> {
                val lastShownSpellId = spellDao.getLastShownSpell()?.id
                val spells = response.data.map { dtoToEntityMapper.toSpell(it, lastShownSpellId) }
                spellDao.insertAll(spells)
                val cachedData = spellDao.search(query)
                cachedData.map(entityToDomainMapper::toSpell)
            }
        }
    }

    override suspend fun getRandomSpell(): Spell? {
        val cached = spellDao.getLastShownSpell()

        val randomSpell = when (val response = apiService.getRandomSpell()) {
            is Result.Error -> cached
            is Result.Success -> {
                cached?.run { spellDao.insert(copy(isLastShown = false)) }
                val spellEntity = dtoToEntityMapper.toSpell(response.data, cached?.id).copy(isLastShown = true)
                spellDao.insert(spellEntity)
                spellEntity
            }
        }
        return if (randomSpell != null) entityToDomainMapper.toSpell(randomSpell) else null
    }
}