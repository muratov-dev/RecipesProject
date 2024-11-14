package dev.ymuratov.recipes.data.repo

import android.app.Application
import com.google.gson.Gson
import dev.ymuratov.recipes.data.db.MainDatabase
import dev.ymuratov.recipes.data.model.RecipesEntity
import dev.ymuratov.recipes.data.model.toDomain
import dev.ymuratov.recipes.data.utils.readJSONFromAssets
import dev.ymuratov.recipes.domain.model.RecipeModel
import dev.ymuratov.recipes.domain.repo.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainRepositoryImpl(application: Application) : MainRepository {

    private val dao = MainDatabase.getInstance(application).mainDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val json = readJSONFromAssets(application, "recipes.json")
            val data = Gson().fromJson(json, RecipesEntity::class.java)
            dao.insert(data)
        }
    }

    override fun getRecipes(searchQuery: String): Flow<List<RecipeModel>> {
        return dao.getRecipes("%$searchQuery%").map { list -> list.map { it.toDomain() } }.distinctUntilChanged()
    }

    override fun getBookmarks(): Flow<List<RecipeModel>> {
        return dao.getBookmarks().map { list -> list.map { it.toDomain() } }.distinctUntilChanged()
    }

    override suspend fun updateRecipe(recipeId: Long, isBooked: Boolean) {
        dao.updateRecipe(recipeId, !isBooked)
    }
}