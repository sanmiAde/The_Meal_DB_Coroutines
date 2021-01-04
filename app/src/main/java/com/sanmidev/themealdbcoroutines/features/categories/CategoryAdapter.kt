package com.sanmidev.themealdbcoroutines.features.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanmidev.themealdbcoroutines.data.model.category.CategoryModel
import com.sanmidev.themealdbcoroutines.data.model.category.CategoryModelDiff
import com.sanmidev.themealdbcoroutines.databinding.CategoryListItemBinding
import com.sanmidev.themealdbcoroutines.utils.CoilImageLoader

typealias OnCategoryClick = (CategoryModel) -> Unit

class CategoryAdapter(
    private val onCategoryClick: OnCategoryClick
) : ListAdapter<CategoryModel, CategoryViewHolder>(CategoryModelDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryListItemBinding.inflate(inflater, parent, false)

        val viewHolder = CategoryViewHolder(binding)

        binding.imgCategory.setOnClickListener {
            val categoryModel = getItem(viewHolder.adapterPosition)
            onCategoryClick.invoke(categoryModel)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bindCategoryModel(category)
    }
}

class CategoryViewHolder(
    private val binding: CategoryListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bindCategoryModel(categoryModel: CategoryModel) {
        binding.txtDescription.text = categoryModel.category
        CoilImageLoader.loadImage(binding.imgCategory, categoryModel.imageUrl)
    }
}