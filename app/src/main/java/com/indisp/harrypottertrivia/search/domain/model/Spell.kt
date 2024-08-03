package com.indisp.harrypottertrivia.search.domain.model

data class Spell(
    val id: Int,
    val name: String,
    val use: String
): Catalog("Spell")
