package com.indisp.harrypottertrivia.search.domain.usecase

import android.util.Log
import com.indisp.harrypottertrivia.search.domain.model.SearchResult
import com.indisp.harrypottertrivia.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class SearchUseCase(
    private val searchRepository: SearchRepository
) {
    private val _searchResultFlow = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResultFlow: StateFlow<List<SearchResult>> = _searchResultFlow

    suspend operator fun invoke(query: String) {
        Log.d("PRODBUG", "invoke: SearchUseCase - $query")
        if (query.isEmpty()) {
            _searchResultFlow.update { emptyList() }
            return
        }
        val books = searchRepository.searchBook(query)
        val characters = searchRepository.searchCharacter(query)
        val spells = searchRepository.searchSpell(query)
        val house = searchRepository.searchHouse(query)

        val result = buildList {
            if (books.isNotEmpty())
                add(SearchResult(books[0].catalogName, books))
            if (characters.isNotEmpty())
                add(SearchResult(characters[0].catalogName, characters))
            if (spells.isNotEmpty())
                add(SearchResult(spells[0].catalogName, spells))
            if (house.isNotEmpty())
                add(SearchResult(house[0].catalogName, house))
        }
        _searchResultFlow.update { result }
    }
}
