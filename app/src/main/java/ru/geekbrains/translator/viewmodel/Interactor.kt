package ru.geekbrains.translator.viewmodel

import ru.geekbrains.translator.model.data.AppState

interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): AppState
}