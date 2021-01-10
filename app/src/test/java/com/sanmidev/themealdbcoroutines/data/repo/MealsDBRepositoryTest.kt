package com.sanmidev.themealdbcoroutines.data.repo


import com.google.common.truth.Truth
import com.sanmidev.themealdbcoroutines.Fixtures
import com.sanmidev.themealdbcoroutines.NetworkFactory
import com.sanmidev.themealdbcoroutines.RepositoryFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

const val NUMBER_OF_ELEMENTS = 5

class MealsDBRepositoryTest {

    @get:Rule
    val mockWebServer = MockWebServer()

    private lateinit var dispatcher: Dispatcher

    private lateinit var CUT: MealsRepository

    private lateinit var fixture: Fixtures

    @Before
    fun setUp() {
        CUT = RepositoryFactory.provideMealsDbRepository(mockWebServer)
        dispatcher = NetworkFactory.provideMockWebServerDispatcher()
        fixture = Fixtures

    }

    @After
    fun tearDown() {

    }

    @Test
    fun getCategories_shouldCallCorrectAPI_whenRequestIsMade() {
        //GIVEN
        mockWebServer.dispatcher = dispatcher

        runBlocking {

            //WHEN
            val categories =  CUT.getCategories()
            val request = mockWebServer.takeRequest()

            //THEN
            Truth.assertThat(request.path).isEqualTo(NetworkFactory.CATEGORIES_LIST_PATH)
            Truth.assertThat(request.method).isEqualTo("GET")
        }

    }

    @Test
    fun getCategories_shouldReturnListOfCategories_whenRequestIsSuccessful() {

        runBlocking {
            //GIVEN
            mockWebServer.dispatcher = dispatcher

            //WHEN
            val categories =  CUT.getCategories()

            //THEN
            Truth.assertThat(categories.size).isEqualTo(NUMBER_OF_ELEMENTS)

        }

    }


    @Test
    fun getCategories_shouldReturnCorrectData_whenRequestIsSuccessful() {

        runBlocking {
            //GIVEN
            mockWebServer.dispatcher = dispatcher

            //WHEN
            val categories =  CUT.getCategories()
            val actualCategory = categories[0]

            //THEN
            val expectedCategory = fixture.categoriesFixture?.categories!![0]
            Truth.assertThat(actualCategory.category).isEqualTo(expectedCategory.strCategory)
            Truth.assertThat(actualCategory.description).isEqualTo(expectedCategory.strCategoryDescription)
            Truth.assertThat(actualCategory.id).isEqualTo(expectedCategory.idCategory)
            Truth.assertThat(actualCategory.imageUrl).isEqualTo(expectedCategory.strCategoryThumb)

        }

    }

    @Test
    fun getMeal() {
    }
}