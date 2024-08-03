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
        val cached = bookDao.search(query)
        if (cached.isNotEmpty())
            return cached.map(entityToDomainMapper::toBook)

        return when(val response = apiService.searchBook(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                emptyList()
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
        val cached = characterDao.search(query)
        if (cached.isNotEmpty())
            return cached.map(entityToDomainMapper::toCharacter)

        return when(val response = apiService.searchCharacter(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                emptyList()
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
        val cached = houseDao.search(query)
        if (cached.isNotEmpty())
            return cached.map(entityToDomainMapper::toHouse)

        return when(val response = apiService.searchHouse(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                emptyList()
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
        val cached = spellDao.search(query)
        if (cached.isNotEmpty())
            return cached.map(entityToDomainMapper::toSpell)

        return when(val response = apiService.searchSpell(query)) {
            is Result.Error -> {
                Log.d("PRODBUG", "searchBook: $${response.error}")
                emptyList()
            }
            is Result.Success -> {
                val spells = response.data.map(dtoToEntityMapper::toSpell)
                spellDao.insertAll(spells)
                val cachedData = spellDao.search(query)
                cachedData.map(entityToDomainMapper::toSpell)
            }
        }
    }
}