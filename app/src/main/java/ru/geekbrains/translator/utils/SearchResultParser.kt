package ru.geekbrains.translator.utils

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.dto.SearchResultDto
import ru.geekbrains.model.data.userdata.DataModel
import ru.geekbrains.model.data.userdata.Meaning
import ru.geekbrains.model.data.userdata.TranslatedMeaning

fun mapSearchResultToResult(searchResults: List<SearchResultDto>): List<DataModel> {
    return searchResults.map { searchResult ->
        var meanings: List<Meaning> = listOf()
        searchResult.meanings?.let { // Дополнительная проверка для
            // HistoryScreen, так как там сейчас не
            // отображаются значения
            meanings = it.map { meaningsDto ->
                Meaning(
                        TranslatedMeaning(meaningsDto?.translation?.translation ?: ""),
                        meaningsDto?.imageUrl ?: ""
                )
            }
        }
        DataModel(searchResult.text ?: "", meanings)
    }
}

fun parseOnlineSearchResults(data: AppState): AppState {
    return AppState.Success(mapResult(data, true))
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
        newSearchDataModels: ArrayList<DataModel>
) {
    val searchDataModels: List<DataModel> = data.data as List<DataModel>
    if (searchDataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchDataModels) {
                parseOnlineResult(searchResult, newSearchDataModels)
            }
        } else {
            for (searchResult in searchDataModels) {
                newSearchDataModels.add(DataModel(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(searchDataModel: DataModel, newSearchDataModels: ArrayList<DataModel>) {
    if (searchDataModel.text.isNotBlank() && searchDataModel.meanings.isNotEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        newMeanings.addAll(searchDataModel.meanings.filter { it.translatedMeaning.translatedMeaning.isNotBlank() })
        if (newMeanings.isNotEmpty()) {
            newSearchDataModels.add(
                    DataModel(
                            searchDataModel.text,
                            newMeanings
                    )
            )
        }
    }
}

fun convertMeaningsToString(meanings: List<Meaning>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translatedMeaning.translatedMeaning, ", ")
        } else {
            meaning.translatedMeaning.translatedMeaning
        }
    }
    return meaningsSeparatedByComma
}
