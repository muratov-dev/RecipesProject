package dev.ymuratov.recipes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.ymuratov.recipes.R
import dev.ymuratov.recipes.databinding.ItemRecipeBinding
import dev.ymuratov.recipes.domain.model.RecipeModel

class RecipesAdapter : ListAdapter<RecipeModel, RecipeViewHolder>(RecipeDiffCallback()) {

    var onBookmarkClickListener: ((Long, Boolean) -> Unit)? = null
    var onItemClickListener: ((RecipeModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        return RecipeViewHolder(ItemRecipeBinding.inflate(layoutInflate, parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            Picasso.get().load(item.image).placeholder(R.drawable.ic_image_placeholder).into(recipeImageView)
            recipeNameTextView.text = item.title
            recipeTimeTextView.text = item.cookTime
            recipeBookmarkButton.apply {
                setImageResource(if (item.isBooked) R.drawable.ic_star_checked else R.drawable.ic_star_unchecked)
                setOnClickListener {
                    onBookmarkClickListener?.invoke(item.id, item.isBooked)
                }
            }
            root.setOnClickListener { onItemClickListener?.invoke(item) }
        }
    }
}

class RecipeViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

class RecipeDiffCallback : DiffUtil.ItemCallback<RecipeModel>() {
    override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel) = oldItem == newItem
}