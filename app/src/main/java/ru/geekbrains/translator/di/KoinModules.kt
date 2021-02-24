package ru.geekbrains.translator.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.translator.model.data.DataModel
import ru.geekbrains.translator.model.datasource.RetrofitImplementation
import ru.geekbrains.translator.model.datasource.RoomDataBaseImplementation
import ru.geekbrains.translator.model.repository.Repository
import ru.geekbrains.translator.model.repository.RepositoryImplementation
import ru.geekbrains.translator.viewmodel.MainInteractor
import ru.geekbrains.translator.viewmodel.MainViewModel

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImplementation(
        RetrofitImplementation()
    ) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImplementation(
        RoomDataBaseImplementation()
    ) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}
