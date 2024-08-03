package com.indisp.harrypottertrivia.search.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indisp.core.DispatcherProvider
import com.indisp.harrypottertrivia.search.domain.model.Catalog
import com.indisp.harrypottertrivia.search.domain.model.SearchResult
import com.indisp.harrypottertrivia.search.domain.model.Spell
import com.indisp.harrypottertrivia.search.domain.usecase.GetRandomSpellUseCase
import com.indisp.harrypottertrivia.search.domain.usecase.SearchUseCase
import com.indisp.harrypottertrivia.search.ui.mapper.PresentableDataMapper
import com.indisp.harrypottertrivia.search.ui.model.PresentableSearchResult
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchUseCase: SearchUseCase,
    private val getRandomSpellUseCase: GetRandomSpellUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val mapper: PresentableDataMapper
) : ViewModel() {

    private companion object {
        val INITIAL_STATE = State(searchQuery = "", searchResult = persistentListOf())
    }
    private var isScreenInitialized = false
    private val _screenStateFlow = MutableStateFlow(INITIAL_STATE)
    val screenStateFlow: StateFlow<State> = _screenStateFlow.asStateFlow()
    private val _sideEffectFlow = MutableStateFlow<SideEffect>(SideEffect.Idle)
    val sideEffectFlow: StateFlow<SideEffect> = _sideEffectFlow.asStateFlow()
    private val _searchQueryFlow = MutableStateFlow("")

    private suspend fun observeSearchQuery() {
        _searchQueryFlow
            .debounce(1000L)
            .distinctUntilChanged()
            .collectLatest { query -> searchUseCase(query) }
    }

    private suspend fun observeSearchResult() {
        searchUseCase.searchResultFlow.collectLatest { result ->
            _screenStateFlow.update { it.copy(searchResult = mapper.toDisplayResult(result)) }
        }
    }

    private fun resetSideEffect() {
        _sideEffectFlow.update { SideEffect.Idle }
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.OnScreenCreated -> {
                if (isScreenInitialized.not()) {
                    isScreenInitialized = true
                    viewModelScope.launch(dispatcherProvider.IO) { observeSearchQuery() }
                    viewModelScope.launch(dispatcherProvider.IO) { observeSearchResult() }
                    viewModelScope.launch(dispatcherProvider.IO) { _screenStateFlow.update { it.copy(randomSpell = getRandomSpellUseCase()) } }
                }
            }
            Event.OnErrorShown -> resetSideEffect()
            Event.OnNavigatedToCatalogDetails -> resetSideEffect()
            is Event.OnSearchQueryChanged -> {
                _screenStateFlow.update { it.copy(searchQuery = event.query) }
                _searchQueryFlow.update { event.query }
            }
            is Event.OnSearchResultClicked -> {
                Log.d("PRODBUG", "onEvent: OnSearchResultClicked - ${event.catalog}")
                _screenStateFlow.update { it.copy(selectedCatalog = event.catalog) }
                _sideEffectFlow.update { SideEffect.NavigateToCatalogDetails }
            }
        }
    }

    data class State(
        val searchQuery: String,
        val searchResult: PersistentList<PresentableSearchResult>,
        val selectedCatalog: Catalog? = null,
        val randomSpell: Spell? = null
    )

    sealed interface Event {
        object OnScreenCreated : Event
        object OnErrorShown: Event
        object OnNavigatedToCatalogDetails: Event
        data class OnSearchQueryChanged(val query: String) : Event
        data class OnSearchResultClicked(val catalog: Catalog) : Event
    }

    sealed interface SideEffect {
        object Idle : SideEffect
        data class ShowError(val message: String) : SideEffect
        object NavigateToCatalogDetails : SideEffect
    }
}