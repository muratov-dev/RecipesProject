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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipes: List<RecipeEntity>)
}