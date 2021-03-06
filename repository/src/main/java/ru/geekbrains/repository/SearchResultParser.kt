package ru.geekbrains.repository

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.model.data.Meanings
import ru.geekbrains.model.data.Translation
import ru.geekbrains.repository.room.HistoryEntity


fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val searchResult = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(DataModel(entity.word, listOf(Meanings(Translation(entity.description), null))))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                searchResult[0].meanings?.get(0)?.translation?.let {
                    HistoryEntity(searchResult[0].text!!, searchResult[0].meanings!![0].translation!!.translation)
                } ?: HistoryEntity(searchResult[0].text!!, null)

            }
        }
        else -> null
    }
}