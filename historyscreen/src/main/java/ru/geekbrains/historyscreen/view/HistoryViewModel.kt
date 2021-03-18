package ru.geekbrains.historyscreen.view

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import ru.geekbrains.core.viewmodel.BaseViewModel
import ru.geekbrains.historyscreen.parseLocalSearchResults
import ru.geekbrains.model.data.AppState

class HistoryViewModel(private val interactor: HistoryInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun getData() {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor() }
    }

    private suspend fun startInteractor() {
        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getData()))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null) // Set View to original state in onStop
        super.onCleared()
    }
}
