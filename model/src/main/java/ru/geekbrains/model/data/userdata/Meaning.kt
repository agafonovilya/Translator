package ru.geekbrains.model.data.userdata

data class Meaning(
        val translatedMeaning: TranslatedMeaning = TranslatedMeaning(),
        val imageUrl: String = ""
)