package com.sanmidev.themealdbcoroutines.data.model.category

import androidx.recyclerview.widget.DiffUtil


data class CategoryModel(
    val id: String,
    val category: String,
    val description: String,
    val imageUrl: String
)

object CategoryModelDiff : DiffUtil.ItemCallback<CategoryModel>(){
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem == newItem
    }
}