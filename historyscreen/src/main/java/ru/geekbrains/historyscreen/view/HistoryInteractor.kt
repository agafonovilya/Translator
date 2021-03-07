package ru.geekbrains.historyscreen.view

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.Repository
import ru.geekbrains.repository.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : IHistoryInteractor<AppState> {

    override suspend fun getData(): AppState {
        return AppState.Success(repositoryLocal.getData())
    }

}

