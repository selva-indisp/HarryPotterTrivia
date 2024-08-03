package com.indisp.harrypottertrivia.search.data.remote

import com.indisp.core.Result
import com.indisp.harrypottertrivia.search.data.remote.dto.BookDto
import com.indisp.harrypottertrivia.search.data.remote.dto.CharacterDto
import com.indisp.harrypottertrivia.search.data.remote.dto.SpellDto
import com.indisp.harrypottertrivia.search.data.remote.dto.HouseDto
import com.indisp.network.NetworkApiService
import com.indisp.network.NetworkFailure

interface SearchApiService {
    suspend fun searchBook(query: String): Result<List<BookDto>, NetworkFailure>
    suspend fun searchCharacter(query: String): Result<List<CharacterDto>, NetworkFailure>
    suspend fun searchSpell(query: String): Result<List<SpellDto>, NetworkFailure>
    suspend fun searchHouse(query: String): Result<List<HouseDto>, NetworkFailure>
    suspend fun getRandomSpell(): Result<SpellDto, NetworkFailure>
}

class SearchApiServiceImpl(
    private val networkApiService: NetworkApiService
): SearchApiService {

    private companion object {
        const val BASE_URL = "https://potterapi-fedeperin.vercel.app/en"
    }
    override suspend fun searchBook(query: String): Result<List<BookDto>, NetworkFailure> {
        return networkApiService.get("$BASE_URL/books?search=$query")
    }

    override suspend fun searchCharacter(query: String): Result<List<CharacterDto>, NetworkFailure> {
        return networkApiService.get("$BASE_URL/characters?search=$query")
    }

    override suspend fun searchSpell(query: String): Result<List<SpellDto>, NetworkFailure> {
        return networkApiService.get("$BASE_URL/spells?search=$query")
    }

    override suspend fun searchHouse(query: String): Result<List<HouseDto>, NetworkFailure> {
        return networkApiService.get("$BASE_URL/houses?search=$query")
    }

    override suspend fun getRandomSpell(): Result<SpellDto, NetworkFailure> {
        return networkApiService.get("$BASE_URL/spells/random")
    }

}