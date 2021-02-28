package ru.geekbrains.translator.viewmodel.history

import ru.geekbrains.translator.model.data.AppState

interface IHistoryInteractor<T> {

    suspend fun getData(): AppState
}