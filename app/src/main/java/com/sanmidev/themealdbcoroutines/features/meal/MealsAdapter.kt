package com.sanmidev.themealdbcoroutines.features.meal


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanmidev.themealdbcoroutines.data.model.meal.MealModel
import com.sanmidev.themealdbcoroutines.data.model.meal.MealModelDiff
import com.sanmidev.themealdbcoroutines.databinding.MealsListItemBinding
import com.sanmidev.themealdbcoroutines.utils.CoilImageLoader

typealias onMealsClicked = (MealModel) -> Unit

class MealsAdapter(private val onMealsClicked: onMealsClicked) : ListAdapter<MealModel, MealViewHolder>(
    MealModelDiff
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MealsListItemBinding.inflate(inflater, parent, false)

        val viewHolder = MealViewHolder(binding)

        binding.imgMeal.setOnClickListener {
            val mealModel = getItem(viewHolder.adapterPosition)
            onMealsClicked(mealModel)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
       val meal = getItem(position)
        holder.bindMealModel(meal)
    }

}

class MealViewHolder(private val binding : MealsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindMealModel(meal: MealModel) {
        binding.txtMealName.text = meal.name
        CoilImageLoader.loadImage(binding.imgMeal, meal.imageUrl)
    }

}
