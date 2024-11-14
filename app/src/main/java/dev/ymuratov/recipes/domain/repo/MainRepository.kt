package dev.ymuratov.recipes.domain.repo

import androidx.lifecycle.LiveData
import dev.ymuratov.recipes.domain.model.RecipeModel

interface MainRepository {
    fun getRecipes(searchQuery: String): LiveData<List<RecipeModel>>
}