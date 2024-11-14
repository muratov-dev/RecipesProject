package dev.ymuratov.recipes.domain.repo

import dev.ymuratov.recipes.domain.model.RecipeModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getRecipes(searchQuery: String): Flow<List<RecipeModel>>

    fun getBookmarks(): Flow<List<RecipeModel>>

    suspend fun updateRecipe(recipeId: Long, isBooked: Boolean)
}