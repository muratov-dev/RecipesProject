package dev.ymuratov.recipes.domain.repo

import dev.ymuratov.recipes.domain.model.RecipeModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getRecipes(searchQuery: String): Flow<List<RecipeModel>>
}