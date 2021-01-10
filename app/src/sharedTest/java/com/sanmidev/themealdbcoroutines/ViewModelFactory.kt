package com.sanmidev.themealdbcoroutines

import com.sanmidev.themealdbcoroutines.data.repo.MealsDBRepository
import com.sanmidev.themealdbcoroutines.features.categories.CategoriesViewModel
import okhttp3.mockwebserver.MockWebServer

object ViewModelFactory {

    fun providesCategoriesViewModel(mockWebServer: MockWebServer) : CategoriesViewModel{
        val mealsDBRepository = RepositoryFactory.provideMealsDbRepository(mockWebServer)
        return CategoriesViewModel(mealsDBRepository)
    }
}