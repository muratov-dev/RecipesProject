package dev.ymuratov.recipes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ymuratov.recipes.data.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Query("select * from recipes where title like :filter")
    fun getRecipes(filter: String): Flow<List<RecipeEntity>>

    @Query("select * from recipes where isBooked = 1")
    fun getBookmarks(): Flow<List<RecipeEntity>>

    @Query("select * from recipes where id = :recipeId")
    fun getRecipeById(recipeId: Long): Flow<RecipeEntity>

    @Query("update recipes set isBooked = :isBooked where id = :id")
    suspend fun updateRecipe(id: Long, isBooked: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipes: List<RecipeEntity>)
}