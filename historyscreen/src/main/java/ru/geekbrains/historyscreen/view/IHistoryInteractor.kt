package ru.geekbrains.historyscreen.view

import ru.geekbrains.model.data.AppState

interface IHistoryInteractor<T> {

    suspend fun getData(): AppState
}