package com.indisp.harrypottertrivia.search.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.indisp.designsystem.components.composable.LifecycleObserver
import com.indisp.designsystem.components.text.DsText
import com.indisp.designsystem.components.text.DsTextType
import com.indisp.designsystem.components.textfield.DsSearchField
import com.indisp.designsystem.resource.Size
import com.indisp.harrypottertrivia.navigation.Route
import com.indisp.harrypottertrivia.search.domain.model.Catalog
import com.indisp.harrypottertrivia.search.domain.model.Spell
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
    navController: NavController,
    onEvent: (Event) -> Unit
) {
    val state by screenStateFlow.collectAsState()
    val context = LocalContext.current

    LifecycleObserver(
        onCreate = { onEvent(Event.OnScreenCreated) }
    )

    LaunchedEffect(Unit) {
        sideEffectFlow.collectLatest { sideEffect ->
            when (sideEffect) {
                SideEffect.Idle -> {}
                SideEffect.NavigateToCatalogDetails -> {
                    onEvent(Event.OnNavigatedToCatalogDetails)
                    navController.navigate(Route.SEARCH_DETAIL.name)
                }
                is SideEffect.ShowError -> { Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            DsSearchField(
                modifier = Modifier.padding(
                    start = Size.medium,
                    top = Size.large,
                    end = Size.medium
                ),
                value = state.searchQuery,
                onValueChange = { query ->
                    onEvent(Event.OnSearchQueryChanged(query))
                },
                hint = "Search by spell/book/character name"
            )
            val modifier = Modifier.padding(padding)
            if (state.searchResult.isNotEmpty())
                SearchResultList(result = state.searchResult, modifier = modifier) { onEvent(Event.OnSearchResultClicked(it))}
            else
                state.randomSpell?.run { SpellComp(spell = this) }
        }
    }
}

@Composable
private fun SpellComp(spell: Spell, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DsText(text = spell.name, type = DsTextType.Title)
        Spacer(modifier = Modifier.height(Size.large))
        DsText(text = spell.use, type = DsTextType.Primary())
    }
}

@Composable
private fun SearchResultList(
    result: PersistentList<PresentableSearchResult>,
    modifier: Modifier = Modifier,
    onClick: (Catalog) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            count = result.size
        ) {
            when (val resultItem = result[it]) {
                is PresentableSearchResult.ResultItem -> SearchResultItem(item = resultItem, onClick)
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
private fun SearchResultItem(
    item: PresentableSearchResult.ResultItem,
    onClick: (Catalog) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = Size.large, vertical = Size.medium)
            .clickable { onClick(item.catalog) }
    ) {
        DsText(text = item.resultItem, type = DsTextType.Primary())
    }
}