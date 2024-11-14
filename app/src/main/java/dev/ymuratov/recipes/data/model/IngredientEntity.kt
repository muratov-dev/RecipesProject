package dev.ymuratov.recipes.data.model

import dev.ymuratov.recipes.domain.model.IngredientModel

data class IngredientEntity(
    val name: String, val quantity: String
)

fun IngredientEntity.toDomain() = IngredientModel(name, quantity)