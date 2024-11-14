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
import com.squareup.picasso.Picasso
import dev.ymuratov.recipes.R
import dev.ymuratov.recipes.databinding.FragmentRecipeBinding
import dev.ymuratov.recipes.ui.MainState
import dev.ymuratov.recipes.ui.MainViewModel
import dev.ymuratov.recipes.ui.adapter.IngredientsAdapter
import dev.ymuratov.recipes.ui.adapter.StepsAdapter
import kotlinx.coroutines.launch

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding: FragmentRecipeBinding
        get() = _binding ?: throw RuntimeException("FragmentRecipeBinding is null")

    private val viewModel: MainViewModel by activityViewModels()
    private val ingredientsAdapter = IngredientsAdapter()
    private val stepsAdapter = StepsAdapter()
    private var isBooked = false
    private var recipeId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
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

    private fun FragmentRecipeBinding.initViews() {
        recipeBackButton.setOnClickListener { findNavController().popBackStack() }
        recipeBookmarkButton.setOnClickListener { viewModel.updateRecipe(recipeId, isBooked) }
        recipeIngredientsRecyclerView.adapter = ingredientsAdapter
        recipeStepsRecyclerView.adapter = stepsAdapter
    }

    private fun handleState(uiState: MainState) {
        with(binding) {
            uiState.selectedRecipe?.let {
                isBooked = it.isBooked
                recipeId = it.id
                Picasso.get().load(it.image).placeholder(R.drawable.ic_image_placeholder).into(recipeImageView)
                recipeBookmarkButton.setImageResource(
                    if (isBooked) R.drawable.ic_star_checked
                    else R.drawable.ic_star_unchecked
                )
                recipeNameTextView.text = it.title
                recipeTotalTimeTextView.text = it.totalTime
                recipeCookTimeTextView.text = it.cookTime
                recipeDescriptionTextView.text = it.description
                ingredientsAdapter.submitList(it.ingredients)
                stepsAdapter.submitList(it.steps)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}