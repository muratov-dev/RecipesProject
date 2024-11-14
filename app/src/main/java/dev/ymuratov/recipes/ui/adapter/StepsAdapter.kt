package dev.ymuratov.recipes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev.ymuratov.recipes.databinding.ItemRecipeStepBinding
import dev.ymuratov.recipes.domain.model.StepModel

class StepsAdapter() : ListAdapter<StepModel, StepViewHolder>(StepDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemRecipeStepBinding.inflate(layoutInflater, parent, false)
        return StepViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            stepTitleTextView.text = item.title
            stepDescriptionTextView.text = item.description
        }
    }
}

class StepViewHolder(val binding: ItemRecipeStepBinding) : ViewHolder(binding.root)

class StepDiffCallback : DiffUtil.ItemCallback<StepModel>() {
    override fun areItemsTheSame(oldItem: StepModel, newItem: StepModel) = oldItem.title == newItem.title
    override fun areContentsTheSame(oldItem: StepModel, newItem: StepModel) = oldItem == newItem
}
