package ru.geekbrains.translator.view.base

import ru.geekbrains.translator.model.data.DataModel

interface View {

    fun showErrorScreen(error: String?)
    fun showSuccessScreen(dataModel: List<DataModel>)
    fun showLoadingScreen(progress: Int?)
}
