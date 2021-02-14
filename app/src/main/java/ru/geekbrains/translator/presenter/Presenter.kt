package ru.geekbrains.translator.presenter

import ru.geekbrains.translator.model.data.AppState
import ru.geekbrains.translator.view.base.View

interface Presenter<T : AppState, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
