package ru.geekbrains.core.viewmodel

import ru.geekbrains.model.data.AppState

interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): AppState
}