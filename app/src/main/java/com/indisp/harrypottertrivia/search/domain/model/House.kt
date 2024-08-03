package com.indisp.harrypottertrivia.search.domain.model

data class House(
    val id: Int,
    val name: String,
    val founder: String,
    val animal: String
): Catalog("House")