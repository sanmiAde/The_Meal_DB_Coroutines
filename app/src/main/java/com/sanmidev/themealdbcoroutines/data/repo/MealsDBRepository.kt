package com.sanmidev.themealdbcoroutines.data.repo

import com.sanmidev.themealdbcoroutines.data.MealsDbService
import com.sanmidev.themealdbcoroutines.data.model.category.CategoryModel
import com.sanmidev.themealdbcoroutines.data.model.meal.MealModel
import com.sanmidev.themealdbcoroutines.data.response.mapper.MealsDbMapper

class MealsDBRepository(private val mealsDbService: MealsDbService, private val mapper : MealsDbMapper) : MealsRepository {
    override suspend fun getCategories(): List<CategoryModel> {
       return mealsDbService.getCategories().categories.map { mapper.mapCategoryResponseToModel(it) }
    }

    override suspend fun getMeal(name: String): List<MealModel> {
        return mealsDbService.getMeal(name).data?.map { mapper.mapMealResponseToModel(it) } ?: emptyList()
    }


}