package dev.ymuratov.recipes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.ymuratov.recipes.data.repo.MainRepositoryImpl
import dev.ymuratov.recipes.domain.model.RecipeModel
import dev.ymuratov.recipes.domain.model.SectionModel
import dev.ymuratov.recipes.domain.repo.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MainState())
    val uiState = _uiState.asStateFlow()

    private val repository: MainRepository = MainRepositoryImpl(application)

    init {
        getRecipes("")
        getBookmarks()
    }

    fun getRecipes(searchQuery: String) = viewModelScope.launch {
        repository.getRecipes(searchQuery).collectLatest { recipes ->
            _uiState.update { it.copy(sections = getSections(recipes)) }
        }
    }

    fun updateRecipe(recipeId: Long, isBooked: Boolean) = viewModelScope.launch {
        repository.updateRecipe(recipeId, isBooked)
    }

    private fun getBookmarks() = viewModelScope.launch {
        repository.getBookmarks().collectLatest { bookmarks ->
            _uiState.update { it.copy(bookmarks = bookmarks) }
        }
    }

    private fun getSections(recipes: List<RecipeModel>): List<SectionModel> {
        val headers = arrayListOf<String>()
        val sections = arrayListOf<SectionModel>()
        recipes.forEach {
            if (headers.contains(it.category)) return@forEach
            headers.add(it.category)
        }
        headers.forEach { header ->
            val recipesByCategory = arrayListOf<RecipeModel>()
            recipes.forEach {
                if (it.category == header) recipesByCategory.add(it)
            }
            sections.add(SectionModel(header, recipesByCategory))
        }
        return sections
    }
}

data class MainState(
    val sections: List<SectionModel> = emptyList(), val bookmarks: List<RecipeModel> = emptyList()
)