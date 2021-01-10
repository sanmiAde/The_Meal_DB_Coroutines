package com.sanmidev.themealdbcoroutines

import com.sanmidev.themealdbcoroutines.data.repo.MealsDBRepository
import com.sanmidev.themealdbcoroutines.data.repo.MealsRepository
import com.sanmidev.themealdbcoroutines.data.response.mapper.MealsDbMapper
import okhttp3.mockwebserver.MockWebServer

object RepositoryFactory {


    fun provideMealsDbRepository(mockWebServer: MockWebServer): MealsDBRepository {

        val bakingRecipeService = NetworkFactory.provideMealsDBService(mockWebServer)

        val mapper = MealsDbMapper

        return MealsDBRepository(
            bakingRecipeService,
            mapper
        )
    }
}