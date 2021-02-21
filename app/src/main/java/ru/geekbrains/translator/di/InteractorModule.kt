package ru.geekbrains.translator.di

import dagger.Module
import dagger.Provides
import ru.geekbrains.translator.model.data.DataModel
import ru.geekbrains.translator.model.repository.Repository
import ru.geekbrains.translator.viewmodel.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
