package dev.passerby.recipes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ymuratov.recipes.databinding.ItemSectionBinding
import dev.ymuratov.recipes.domain.model.RecipeModel
import dev.ymuratov.recipes.domain.model.SectionModel
import dev.ymuratov.recipes.ui.adapter.RecipesAdapter

class SectionsAdapter : ListAdapter<SectionModel, CategoryViewHolder>(CategoryDiffCallBack()) {

    var onBookmarkClickListener: ((Long, Boolean) -> Unit)? = null
    var onItemClickListener: ((RecipeModel) -> Unit)? = null
    private var recipeListAdapter: RecipesAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        return CategoryViewHolder(ItemSectionBinding.inflate(layoutInflate, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        recipeListAdapter = RecipesAdapter().apply {
            onBookmarkClickListener = this@SectionsAdapter.onBookmarkClickListener
            onItemClickListener = this@SectionsAdapter.onItemClickListener
        }
        val item = getItem(position)
        with(holder.binding) {
            sectionNameTextView.text = item.name
            sectionRecyclerView.adapter = recipeListAdapter
            recipeListAdapter?.submitList(item.recipes)
        }
    }
}

class CategoryViewHolder(val binding: ItemSectionBinding) : RecyclerView.ViewHolder(binding.root)

class CategoryDiffCallBack : DiffUtil.ItemCallback<SectionModel>() {
    override fun areItemsTheSame(oldItem: SectionModel, newItem: SectionModel) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: SectionModel, newItem: SectionModel) = oldItem == newItem
}