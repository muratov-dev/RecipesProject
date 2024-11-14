package dev.ymuratov.recipes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev.ymuratov.recipes.databinding.ItemIngredientBinding
import dev.ymuratov.recipes.domain.model.IngredientModel

class IngredientsAdapter() : ListAdapter<IngredientModel, IngredientViewHolder>(IngredientDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = getItem(position)
        val ingredient = "${item.name}: ${item.quantity}"
        holder.binding.ingredientTextView.text = ingredient
    }
}

class IngredientViewHolder(val binding: ItemIngredientBinding) : ViewHolder(binding.root)

class IngredientDiffCallback : DiffUtil.ItemCallback<IngredientModel>() {
    override fun areItemsTheSame(oldItem: IngredientModel, newItem: IngredientModel) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: IngredientModel, newItem: IngredientModel) = oldItem == newItem
}