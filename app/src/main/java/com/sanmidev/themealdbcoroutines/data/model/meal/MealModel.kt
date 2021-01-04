package com.sanmidev.themealdbcoroutines.data.model.meal

import androidx.recyclerview.widget.DiffUtil


data class MealModel(
    val id : String,
    val name : String,
    val imageUrl : String
)

object MealModelDiff : DiffUtil.ItemCallback<MealModel>(){
    override fun areItemsTheSame(oldItem: MealModel, newItem: MealModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealModel, newItem: MealModel): Boolean {
        return oldItem == newItem
    }

}

