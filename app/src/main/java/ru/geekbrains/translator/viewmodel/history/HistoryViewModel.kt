package ru.geekbrains.translator.viewmodel.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import ru.geekbrains.translator.model.data.AppState
import ru.geekbrains.translator.utils.parseLocalSearchResults
import ru.geekbrains.translator.viewmodel.BaseViewModel

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
