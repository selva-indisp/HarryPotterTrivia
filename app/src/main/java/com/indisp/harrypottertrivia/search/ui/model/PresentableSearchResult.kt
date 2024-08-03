package com.indisp.harrypottertrivia.search.ui.model

sealed interface PresentableSearchResult {
    data class Title(val name: String): PresentableSearchResult
    data class ResultItem(val resultItem: String): PresentableSearchResult
}