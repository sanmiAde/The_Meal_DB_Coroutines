package com.sanmidev.themealdbcoroutines

import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.sanmidev.themealdbcoroutines.data.model.category.CategoryModel
import com.sanmidev.themealdbcoroutines.data.model.meal.MealModel
import com.sanmidev.themealdbcoroutines.data.response.categories.CategoriesResponse
import com.sanmidev.themealdbcoroutines.data.response.meal.MealResponse
import com.sanmidev.themealdbcoroutines.data.response.meal.MealsResponse

object Fixtures {
   private val fixture = kotlinFixture {
        nullabilityStrategy(NeverNullStrategy)
    }

    val mealsResponseFixture by lazy { fixture<MealsResponse?>() }

    val mealsModelListFixture by lazy { fixture<List<MealModel>>() }

    val categoriesFixture by lazy { fixture<CategoriesResponse?>() }

    val categoryModelListResponse by lazy { fixture<List<CategoryModel>>() }
}