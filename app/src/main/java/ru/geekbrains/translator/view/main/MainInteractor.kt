package ru.geekbrains.translator.view.main


import io.reactivex.Observable
import ru.geekbrains.translator.model.data.AppState
import ru.geekbrains.translator.model.data.DataModel
import ru.geekbrains.translator.model.repository.Repository
import ru.geekbrains.translator.presenter.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
