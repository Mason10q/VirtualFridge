package ru.virtual.feature_recipes.domain.entities

data class Filter (
    val title: String,
    val filterNames: List<FilterName>
)