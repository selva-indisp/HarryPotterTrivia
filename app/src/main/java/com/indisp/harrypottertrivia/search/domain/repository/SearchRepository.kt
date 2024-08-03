package com.indisp.harrypottertrivia.search.domain.repository

import com.indisp.harrypottertrivia.search.domain.model.Book
import com.indisp.harrypottertrivia.search.domain.model.Character
import com.indisp.harrypottertrivia.search.domain.model.House
import com.indisp.harrypottertrivia.search.domain.model.Spell

interface SearchRepository {
    suspend fun searchBook(query: String): List<Book>
    suspend fun searchCharacter(query: String): List<Character>
    suspend fun searchHouse(query: String): List<House>
    suspend fun searchSpell(query: String): List<Spell>
    suspend fun getRandomSpell(): Spell?
}