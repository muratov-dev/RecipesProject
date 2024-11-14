package dev.ymuratov.recipes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.ymuratov.recipes.domain.model.RecipeModel

@Entity(tableName = "recipes")
data class RecipeEntity(
    val category: String,
    val cookTime: String,
    val description: String,
    @PrimaryKey val id: Long,
    val image: String,
    val ingredients: List<IngredientEntity>,
    val steps: List<StepEntity>,
    val title: String,
    val totalTime: String,
    var isBooked: Boolean = false
)

fun RecipeEntity.toDomain() = RecipeModel(
    category = this.category,
    cookTime = this.cookTime,
    description = this.description,
    id = this.id,
    image = this.image,
    ingredients = this.ingredients.map { it.toDomain() },
    steps = this.steps.map { it.toDomain() },
    title = this.title,
    totalTime = this.totalTime,
    isBooked = this.isBooked,
)
