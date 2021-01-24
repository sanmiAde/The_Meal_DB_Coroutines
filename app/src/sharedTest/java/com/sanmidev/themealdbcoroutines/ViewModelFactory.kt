package com.sanmidev.themealdbcoroutines

import com.sanmidev.themealdbcoroutines.data.repo.MealsDBRepository
import com.sanmidev.themealdbcoroutines.features.categories.CategoriesViewModel
import com.sanmidev.themealdbcoroutines.features.meal.MealsViewModel
import okhttp3.mockwebserver.MockWebServer

object ViewModelFactory {


    fun providesCategoriesViewModel(mockWebServer: MockWebServer) : CategoriesViewModel{
        val mealsDBRepository = RepositoryFactory.provideMealsDbRepository(mockWebServer)
        return CategoriesViewModel(mealsDBRepository)
    }

    fun providesMealsViewModel(mockWebServer: MockWebServer) : MealsViewModel {
        val mealsDBRepository = RepositoryFactory.provideMealsDbRepository(mockWebServer)
        return MealsViewModel(mealsDBRepository, NetworkFactory.MEAL_PATH_QUERY_PARAM)
    }

    fun providesIncorrectMealsViewModel(mockWebServer: MockWebServer) : MealsViewModel {
        val mealsDBRepository = RepositoryFactory.provideMealsDbRepository(mockWebServer)
        return MealsViewModel(mealsDBRepository, NetworkFactory.WRONG_MEAL_QUERY_PARAM)
    }


}