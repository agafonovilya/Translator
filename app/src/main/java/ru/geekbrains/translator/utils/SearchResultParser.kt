package ru.geekbrains.translator.utils

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.model.data.Meanings

fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<DataModel>()
    when (state) {
        is AppState.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
    }

    return AppState.Success(newSearchResults)
}

private fun parseResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

private fun mapResult(
    data: AppState,
    isOnline: Boolean
): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (data) {
        is AppState.Success -> {
            getSuccessResultData(data, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<DataModel>
) {
    val dataModels: List<DataModel> = data.data as List<DataModel>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(DataModel(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma
}
