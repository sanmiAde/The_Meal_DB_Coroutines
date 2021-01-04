package com.sanmidev.themealdbcoroutines.data

import com.sanmidev.themealdbcoroutines.data.response.categories.CategoriesResponse
import com.sanmidev.themealdbcoroutines.data.response.meal.MealResponse
import com.sanmidev.themealdbcoroutines.data.response.meal.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsDbService {

    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories() : CategoriesResponse

    @GET("api/json/v1/1/filter.php")
    suspend fun getMeal(@Query("c") name : String) : MealsResponse
}