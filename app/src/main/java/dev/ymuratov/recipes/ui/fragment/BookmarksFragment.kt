package dev.ymuratov.recipes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.ymuratov.recipes.databinding.FragmentBookmarksBinding
import dev.ymuratov.recipes.ui.MainState
import dev.ymuratov.recipes.ui.MainViewModel
import dev.ymuratov.recipes.ui.adapter.RecipesAdapter
import dev.ymuratov.recipes.ui.fragment.BookmarksFragmentDirections.actionBookmarksFragmentToRecipeFragment
import kotlinx.coroutines.launch

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding: FragmentBookmarksBinding
        get() = _binding ?: throw RuntimeException("FragmentBookmarksBinding is null")

    private val viewModel: MainViewModel by activityViewModels()
    private val recipesAdapter = RecipesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
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

    private fun FragmentBookmarksBinding.initViews() {
        recipesAdapter.apply {
            onBookmarkClickListener = { recipeId, isBooked -> viewModel.updateRecipe(recipeId, isBooked) }
            onItemClickListener = { recipe ->
                viewModel.selectRecipe(recipe)
                findNavController().navigate(actionBookmarksFragmentToRecipeFragment())
            }
        }
        bookmarksBackButton.setOnClickListener { findNavController().popBackStack() }
        bookmarksRecyclerView.adapter = recipesAdapter
    }

    private fun handleState(uiState: MainState) {
        recipesAdapter.submitList(uiState.bookmarks)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}