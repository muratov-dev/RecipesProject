package dev.ymuratov.recipes.data.model

import dev.ymuratov.recipes.domain.model.StepModel

data class StepEntity(
    val description: String, val title: String
)

fun StepEntity.toDomain() = StepModel(description, title)
