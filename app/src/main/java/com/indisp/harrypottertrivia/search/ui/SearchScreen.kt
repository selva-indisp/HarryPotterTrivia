package com.indisp.harrypottertrivia.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.indisp.designsystem.components.composable.LifecycleObserver
import com.indisp.designsystem.components.text.DsText
import com.indisp.designsystem.components.text.DsTextType
import com.indisp.designsystem.components.textfield.DsSearchField
import com.indisp.designsystem.resource.Size
import com.indisp.harrypottertrivia.search.domain.model.SearchResult
import com.indisp.harrypottertrivia.search.ui.SearchViewModel.Event
import com.indisp.harrypottertrivia.search.ui.SearchViewModel.SideEffect
import com.indisp.harrypottertrivia.search.ui.SearchViewModel.State
import com.indisp.harrypottertrivia.search.ui.model.PresentableSearchResult
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    screenStateFlow: StateFlow<State>,
    sideEffectFlow: StateFlow<SideEffect>,
    onEvent: (Event) -> Unit
) {
    val state by screenStateFlow.collectAsState()

    LifecycleObserver(
        onCreate = { onEvent(Event.OnScreenCreated) }
    )

    LaunchedEffect(Unit) {
        sideEffectFlow.collectLatest { sideEffect ->
            when (sideEffect) {
                SideEffect.Idle -> {}
                SideEffect.NavigateToCatalogDetails -> TODO()
                is SideEffect.ShowError -> TODO()
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            DsSearchField(
                modifier = Modifier.padding(start = Size.medium, top = Size.large, end = Size.medium),
                value = state.searchQuery,
                onValueChange = { query ->
                    onEvent(Event.OnSearchQueryChanged(query))
                },
                hint = "Search by spell/book/character name"
            )
            SearchResultList(result = state.searchResult, modifier = Modifier.padding(padding))
        }
    }
}

@Composable
private fun SearchResultList(result: PersistentList<PresentableSearchResult>, modifier: Modifier = Modifier) {
    if (result.isEmpty())
        return
    LazyColumn(
        modifier = modifier
    ) {
        items(
            count = result.size
        ) {
            when (val resultItem = result[it]) {
                is PresentableSearchResult.ResultItem -> SearchResultItem(item = resultItem)
                is PresentableSearchResult.Title -> SearchResultItemTitle(title = resultItem.name)
            }
        }
    }
}

@Composable
private fun SearchResultItemTitle(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(Size.large)
    ) {
        DsText(text = title, type = DsTextType.Title)
    }
}

@Composable
private fun SearchResultItem(item: PresentableSearchResult.ResultItem, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(horizontal = Size.large, vertical = Size.medium)
    ) {
        DsText(text = item.resultItem, type = DsTextType.Primary())
    }
}