package com.indisp.harrypottertrivia.search.domain.usecase

import com.indisp.core.DispatcherProvider
import com.indisp.harrypottertrivia.search.domain.model.SearchResult
import com.indisp.harrypottertrivia.search.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUseCase(
    private val searchRepository: SearchRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    private companion object {
        val INIT_VALUE = SearchQueryResult.SearchResultFound(emptyList())
    }

    private val _searchResultFlow = MutableStateFlow<SearchQueryResult>(INIT_VALUE)
    val searchResultFlow: StateFlow<SearchQueryResult> = _searchResultFlow

    operator fun invoke(query: String, scope: CoroutineScope) =
        scope.launch(dispatcherProvider.IO) {
            if (query.isEmpty()) {
                _searchResultFlow.update { INIT_VALUE }
                return@launch
            }

            val booksJob = async { searchRepository.searchBook(query) }
            val charactersJob = async { searchRepository.searchCharacter(query) }
            val spellsJob = async { searchRepository.searchSpell(query) }
            val houseJob = async { searchRepository.searchHouse(query) }

            launch {
                val books = booksJob.await()
                val characters = charactersJob.await()
                val spells = spellsJob.await()
                val house = houseJob.await()
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
                _searchResultFlow.update {
                    if (result.isEmpty())
                        SearchQueryResult.NoItemsFound()
                    else
                        SearchQueryResult.SearchResultFound(result)
                }
            }
        }
}

sealed interface SearchQueryResult {
    data class SearchResultFound(val data: List<SearchResult>) : SearchQueryResult
    class NoItemsFound : SearchQueryResult
}
