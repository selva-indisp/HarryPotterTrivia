package com.indisp.harrypottertrivia.search.di

import androidx.room.Room
import com.indisp.core.DefaultDispatcher
import com.indisp.harrypottertrivia.search.data.SearchRepositoryImpl
import com.indisp.harrypottertrivia.search.data.local.MainDatabase
import com.indisp.harrypottertrivia.search.data.local.dao.BookDao
import com.indisp.harrypottertrivia.search.data.local.dao.CharacterDao
import com.indisp.harrypottertrivia.search.data.local.dao.HouseDao
import com.indisp.harrypottertrivia.search.data.local.dao.SpellDao
import com.indisp.harrypottertrivia.search.data.mapper.DtoToEntityMapper
import com.indisp.harrypottertrivia.search.data.mapper.EntityToDomainMapper
import com.indisp.harrypottertrivia.search.data.remote.SearchApiService
import com.indisp.harrypottertrivia.search.data.remote.SearchApiServiceImpl
import com.indisp.harrypottertrivia.search.domain.repository.SearchRepository
import com.indisp.harrypottertrivia.search.domain.usecase.GetRandomSpellUseCase
import com.indisp.harrypottertrivia.search.domain.usecase.SearchUseCase
import com.indisp.harrypottertrivia.search.ui.SearchViewModel
import com.indisp.harrypottertrivia.search.ui.mapper.PresentableDataMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchDiModule = module {
    viewModel<SearchViewModel> {
        SearchViewModel(get(), get(), DefaultDispatcher, get())
    }
    factory { SearchUseCase(get()) }
    factory { GetRandomSpellUseCase(get()) }
    factory { PresentableDataMapper() }
}

val searchDataDiModule = module {
    single<MainDatabase> {
        Room.databaseBuilder(
            context = get(),
            MainDatabase::class.java,
            "main_database"
        ).build()
    }
    factory<SearchRepository> {
        SearchRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
            EntityToDomainMapper(),
            DtoToEntityMapper()
        )
    }
    factory<SearchApiService> { SearchApiServiceImpl(get()) }
    factory<BookDao> { get<MainDatabase>().bookDao() }
    factory<CharacterDao> { get<MainDatabase>().characterDao() }
    factory<HouseDao> { get<MainDatabase>().houseDao() }
    factory<SpellDao> { get<MainDatabase>().spellDao() }
}