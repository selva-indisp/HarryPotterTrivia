package com.indisp.harrypottertrivia.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.indisp.designsystem.components.appbar.DsAppBar
import com.indisp.designsystem.components.text.DsText
import com.indisp.designsystem.components.text.DsTextType
import com.indisp.designsystem.resource.Size
import com.indisp.harrypottertrivia.R
import com.indisp.harrypottertrivia.search.domain.model.Book
import com.indisp.harrypottertrivia.search.domain.model.Character
import com.indisp.harrypottertrivia.search.domain.model.House
import com.indisp.harrypottertrivia.search.domain.model.Spell
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchItemDetailScreen(
    navController: NavController,
    screenStateFlow: StateFlow<SearchViewModel.State>
) {
    val state by screenStateFlow.collectAsState()
    val catalog = state.selectedCatalog
    catalog ?: return

    val scrollState = rememberScrollState()
    Scaffold (
        topBar = { DsAppBar(navController = navController, title = catalog.catalogName) }
    ) { padding ->
        Column (modifier = Modifier
            .padding(padding)
            .verticalScroll(scrollState)) {
            when (catalog) {
                is Book -> BookDetail(book = catalog)
                is Character -> CharacterDetail(character = catalog)
                is House -> HouseDetail(house = catalog)
                is Spell -> SpellDetail(spell = catalog)
            }
        }
    }
}

@Composable
private fun ColumnScope.BookDetail(book: Book, modifier: Modifier = Modifier) {
    AsyncImage(
        model = book.coverImageUrl,
        contentDescription = "",
        contentScale = ContentScale.Fit,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        error = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    )
    Spacer(modifier = Modifier.height(Size.large))
    DsText(text = book.title, type = DsTextType.Title)
    Spacer(modifier = Modifier.height(Size.large))
    DsText(text = book.description, type = DsTextType.Primary())
}

@Composable
private fun ColumnScope.CharacterDetail(character: Character, modifier: Modifier = Modifier) {
    AsyncImage(
        model = character.imageUrl,
        contentDescription = "",
        contentScale = ContentScale.Fit,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        error = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
    )
    Spacer(modifier = Modifier.height(Size.large))
    DsText(text = character.dateOfBirth, type = DsTextType.Primary())
    Spacer(modifier = Modifier.height(Size.large))
    DsText(text = character.fullName, type = DsTextType.Primary())
}

@Composable
fun ColumnScope.SpellDetail(spell: Spell, modifier: Modifier = Modifier) {
    DsText(text = spell.name, type = DsTextType.Primary())
    Spacer(modifier = Modifier.height(Size.large))
    DsText(text = spell.use, type = DsTextType.Primary())
}

@Composable
fun ColumnScope.HouseDetail(house: House, modifier: Modifier = Modifier) {
    DsText(text = house.name, type = DsTextType.Primary())
    Spacer(modifier = Modifier.height(Size.large))
    DsText(text = house.founder, type = DsTextType.Primary())
}