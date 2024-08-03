package com.indisp.harrypottertrivia.search.data

import com.indisp.harrypottertrivia.search.domain.model.Book
import com.indisp.harrypottertrivia.search.domain.model.Character
import com.indisp.harrypottertrivia.search.domain.model.House
import com.indisp.harrypottertrivia.search.domain.model.Spell
import com.indisp.harrypottertrivia.search.domain.repository.SearchRepository

class MockRepositoryImpl: SearchRepository {

    override suspend fun searchBook(query: String): List<Book> {
        return emptyList()
    }

    override suspend fun searchCharacter(query: String): List<Character> {
        return emptyList()
    }

    override suspend fun searchHouse(query: String): List<House> {
        return listOf(
            House(id = 1, name = query, founder = "Founder 1", animal = "Animal 1"),
            House(id = 2, name = query, founder = "Founder 1", animal = "Animal 1"),
        )
    }

    override suspend fun searchSpell(query: String): List<Spell> {
        return listOf(
            Spell(id = 1, name = query, use = "Use 1"),
            Spell(id = 2, name = query, use = "Use 2"),
        )
    }
}