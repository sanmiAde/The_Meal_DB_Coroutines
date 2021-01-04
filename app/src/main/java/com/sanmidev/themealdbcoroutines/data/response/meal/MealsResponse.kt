package com.sanmidev.themealdbcoroutines.data.response.meal

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MealsResponse(
    @Json(name = "meals")
    val data : List<MealResponse>?
)