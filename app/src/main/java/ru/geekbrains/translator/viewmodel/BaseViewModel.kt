package ru.geekbrains.translator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.geekbrains.translator.model.data.AppState
import ru.geekbrains.translator.rx.SchedulerProvider

abstract class BaseViewModel<T : AppState>(
    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected open val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected open val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    val viewState: LiveData<T> = liveDataForViewToObserve

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}