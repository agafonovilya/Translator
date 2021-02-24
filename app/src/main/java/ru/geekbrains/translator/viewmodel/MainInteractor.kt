package ru.geekbrains.translator.viewmodel

import ru.geekbrains.translator.model.data.AppState
import ru.geekbrains.translator.model.data.DataModel
import ru.geekbrains.translator.model.repository.Repository

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}
