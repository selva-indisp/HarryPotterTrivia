package com.indisp.harrypottertrivia.search.ui.model

import com.indisp.harrypottertrivia.search.domain.model.Catalog

sealed interface PresentableSearchResult {
    data class Title(val name: String): PresentableSearchResult
    data class ResultItem(val resultItem: String, val catalog: Catalog): PresentableSearchResult
}