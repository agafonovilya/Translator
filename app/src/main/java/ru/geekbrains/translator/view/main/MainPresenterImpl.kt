package ru.geekbrains.translator.view.main

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import ru.geekbrains.translator.model.data.AppState
import ru.geekbrains.translator.model.datasource.DataSourceLocal
import ru.geekbrains.translator.model.datasource.DataSourceRemote
import ru.geekbrains.translator.model.repository.RepositoryImplementation
import ru.geekbrains.translator.presenter.Presenter
import ru.geekbrains.translator.rx.SchedulerProvider
import ru.geekbrains.translator.view.base.View

class MainPresenterImpl<T : AppState, V : View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.showLoadingScreen(null) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                when (appState) {
                    is AppState.Success -> {
                        val dataModel = appState.data
                        if (dataModel == null || dataModel.isEmpty()) {
                            currentView?.showErrorScreen(null)
                        } else {
                            currentView?.showSuccessScreen(dataModel)
                        }
                    }
                    is AppState.Loading -> {
                        currentView?.showLoadingScreen(appState.progress)
                    }
                    is AppState.Error -> {
                        currentView?.showErrorScreen(appState.error.message)
                    }
                }
            }

            override fun onError(e: Throwable) {
                currentView?.showErrorScreen(e.message)
            }

            override fun onComplete() {
            }
        }
    }
}
