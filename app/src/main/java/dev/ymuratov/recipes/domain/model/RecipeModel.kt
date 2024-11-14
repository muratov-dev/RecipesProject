package dev.ymuratov.recipes.domain.model

data class RecipeModel(
    val category: String,
    val cookTime: String,
    val description: String,
    val id: Int,
    val image: String,
    val ingredients: List<IngredientModel>,
    val steps: List<StepModel>,
    val title: String,
    val totalTime: String,
    var isBooked: Boolean = false
)
