package com.indisp.harrypottertrivia.search.domain.model

data class SearchResult(
    val catalogName: String,
    val result: List<Catalog>
)
