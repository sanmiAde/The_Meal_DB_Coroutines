package com.sanmidev.themealdbcoroutines.data.response.mapper

import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth
import com.sanmidev.themealdbcoroutines.data.response.categories.CategoriesResponse
import com.sanmidev.themealdbcoroutines.data.response.categories.CategoryResponse
import com.sanmidev.themealdbcoroutines.data.response.meal.MealResponse
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class MealsDbMapperTest {

    val fixture = kotlinFixture {
        nullabilityStrategy(NeverNullStrategy)
    }

    val SUT = MealsDbMapper

    @Test
    fun mapCategoryResponseToModel() {

        //GIVEN
        val categoryResponse = fixture<CategoryResponse>()

        //WHEN
        val categoryModel = SUT.mapCategoryResponseToModel(categoryResponse)

        //THEN
        Truth.assertThat(categoryResponse.idCategory).isEqualTo(categoryModel.id)
        Truth.assertThat(categoryResponse.strCategory).isEqualTo(categoryModel.category)
        Truth.assertThat(categoryResponse.strCategoryDescription).isEqualTo(categoryModel.description)
        Truth.assertThat(categoryResponse.strCategoryThumb).isEqualTo(categoryModel.imageUrl)

    }

    @Test
    fun mapMealResponseToModel() {
        //GIVEN
        val mealResponse = fixture<MealResponse>()

        //WHEN
        val mealModel = SUT.mapMealResponseToModel(mealResponse)

        //THEN
        Truth.assertThat(mealResponse.idMeal).isEqualTo(mealModel.id)
        Truth.assertThat(mealResponse.strMeal).isEqualTo(mealModel.name)
        Truth.assertThat(mealResponse.strMealThumb).isEqualTo(mealModel.imageUrl)
    }
}