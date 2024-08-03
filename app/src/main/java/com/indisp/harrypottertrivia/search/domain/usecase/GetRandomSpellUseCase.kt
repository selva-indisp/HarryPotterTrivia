package com.indisp.harrypottertrivia.search.domain.usecase

import com.indisp.harrypottertrivia.search.domain.repository.SearchRepository

data class GetRandomSpellUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.getRandomSpell()
}