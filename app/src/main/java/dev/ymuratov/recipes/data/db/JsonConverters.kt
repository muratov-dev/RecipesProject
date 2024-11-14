package dev.ymuratov.recipes.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import dev.ymuratov.recipes.data.model.IngredientEntity
import dev.ymuratov.recipes.data.model.StepEntity

class JsonConverters {
    @TypeConverter
    fun ingredientsListToJson(value: List<IngredientEntity>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToIngredientList(value: String) = Gson().fromJson(value, Array<IngredientEntity>::class.java).toList()

    @TypeConverter
    fun stepsListToJson(value: List<StepEntity>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToStepsList(value: String) = Gson().fromJson(value, Array<StepEntity>::class.java).toList()
}