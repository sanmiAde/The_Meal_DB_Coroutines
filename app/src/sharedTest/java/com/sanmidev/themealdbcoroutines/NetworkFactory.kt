package com.sanmidev.themealdbcoroutines

import com.sanmidev.themealdbcoroutines.data.MealsDbService
import com.sanmidev.themealdbcoroutines.data.response.categories.CategoriesResponseJsonAdapter
import com.sanmidev.themealdbcoroutines.data.response.meal.MealsResponseJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

object NetworkFactory {
    internal const val CATEGORIES_LIST_PATH = "/api/json/v1/1/categories.php"
    internal const val MEAL_PATH_QUERY_PARAM = "Seafood"
    interface const val WRONG_MEAL_QUERY_PARAM = "wrong"
    internal const val MEAL_PATH = "/api/json/v1/1/filter.php?c=$MEAL_PATH_QUERY_PARAM"
    internal const val GET_REQUEST = "GET"
    internal const val POST_REQUEST = "POST"
    private const val MEAL_DETAIL_QUERY_PARAM = "1234"
    private const val MEAL_DETAIL_PATH = "/api/json/v1/1/lookup.php?i=$MEAL_DETAIL_QUERY_PARAM"

    private val moshi by lazy {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    private val mealsResponseAdapter by lazy { MealsResponseJsonAdapter(moshi) }

    private val categoriesResponseJsonAdapter by lazy { CategoriesResponseJsonAdapter(moshi) }

    private fun provideRetrofit(mockWebServer: MockWebServer): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun provideMealsDBService(mockWebServer: MockWebServer): MealsDbService {
        return provideRetrofit(mockWebServer).create(MealsDbService::class.java)
    }

    fun MockWebServer.provideErrorResponse() {
        enqueue(
            MockResponse()
                .setBody("No records found")
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        )
    }

    fun provideMockWebServerDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when {
                    request.path?.contains(CATEGORIES_LIST_PATH)!! -> {
                        val categoryListResponse = Fixtures.categoriesFixture

                        val successJson = categoriesResponseJsonAdapter.toJson(categoryListResponse)

                        MockResponse().setBody(
                            successJson
                        ).setResponseCode(HttpURLConnection.HTTP_OK)
                    }

                    request.path?.contains(MEAL_PATH)!! -> {
                        val mealListResponse = Fixtures.mealsResponseFixture

                        val successJson = mealsResponseAdapter.toJson(mealListResponse)

                        MockResponse().setBody(
                            successJson
                        ).setResponseCode(HttpURLConnection.HTTP_OK)
                    }

                    else -> {
                        MockResponse().setBody(
                            "Content Not Found"
                        ).setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                    }
                }
            }
        }
    }

}