package com.indisp.harrypottertrivia.search.ui.mapper

import com.indisp.harrypottertrivia.search.domain.model.Book
import com.indisp.harrypottertrivia.search.domain.model.Catalog
import com.indisp.harrypottertrivia.search.domain.model.Character
import com.indisp.harrypottertrivia.search.domain.model.House
import com.indisp.harrypottertrivia.search.domain.model.SearchResult
import com.indisp.harrypottertrivia.search.domain.model.Spell
import com.indisp.harrypottertrivia.search.ui.model.PresentableSearchResult
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

class PresentableDataMapper {

    fun toDisplayResult(result: List<SearchResult>): PersistentList<PresentableSearchResult> {
        val presentableResult = mutableListOf<PresentableSearchResult>()
        for (resultItem in result) {
            presentableResult.add(PresentableSearchResult.Title(resultItem.catalogName))
            for (catalogItem in resultItem.result)
                presentableResult.add(PresentableSearchResult.ResultItem(toDisplayName(catalogItem)))
        }
        return presentableResult.toPersistentList()
    }

    private fun toDisplayName(catalog: Catalog): String {
        return when (catalog) {
            is Book -> catalog.title
            is Character -> catalog.fullName
            is House -> catalog.name
            is Spell -> catalog.name
        }
    }
}