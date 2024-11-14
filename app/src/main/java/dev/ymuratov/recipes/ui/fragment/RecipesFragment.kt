package dev.ymuratov.recipes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.ymuratov.recipes.databinding.FragmentRecipesBinding
import dev.ymuratov.recipes.ui.MainState
import dev.ymuratov.recipes.ui.MainViewModel
import dev.ymuratov.recipes.ui.adapter.SectionsAdapter
import dev.ymuratov.recipes.ui.fragment.RecipesFragmentDirections.actionRecipesFragmentToBookmarksFragment
import dev.ymuratov.recipes.ui.fragment.RecipesFragmentDirections.actionRecipesFragmentToRecipeFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding: FragmentRecipesBinding
        get() = _binding ?: throw RuntimeException("FragmentRecipesBinding is null")

    private val viewModel: MainViewModel by activityViewModels()
    private val sectionsAdapter = SectionsAdapter()
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect(::handleState)
                }
            }
        }
    }

    private fun FragmentRecipesBinding.initViews() {
        sectionsAdapter.apply {
            onBookmarkClickListener = { recipeId, isBooked -> viewModel.updateRecipe(recipeId, isBooked) }
            onItemClickListener = { recipe ->
                viewModel.selectRecipe(recipe)
                findNavController().navigate(actionRecipesFragmentToRecipeFragment())
            }
        }
        recipesBookmarksButton.setOnClickListener {
            findNavController().navigate(actionRecipesFragmentToBookmarksFragment())
        }
        recipesSearchEditText.doOnTextChanged { text, _, _, _ ->
            val searchText = text.toString().trim().lowercase()
            if (searchText == searchQuery) return@doOnTextChanged

            searchQuery = searchText

            lifecycleScope.launch {
                delay(300)
                if (searchText != searchQuery) return@launch
                viewModel.getRecipes(searchQuery)
            }
        }
        recipesRecyclerView.adapter = sectionsAdapter
    }

    private fun handleState(uiState: MainState) {
        sectionsAdapter.submitList(uiState.sections)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}