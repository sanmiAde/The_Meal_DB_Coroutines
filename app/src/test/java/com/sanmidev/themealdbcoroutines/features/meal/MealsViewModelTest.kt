package com.sanmidev.themealdbcoroutines.features.meal

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.sanmidev.themealdbcoroutines.NetworkFactory
import com.sanmidev.themealdbcoroutines.ViewModelFactory
import com.sanmidev.themealdbcoroutines.utils.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalCoroutinesApi
@ExperimentalTime
class MealsViewModelTest {

    private lateinit var CUT : MealsViewModel

    private lateinit var dispatcher: Dispatcher

    @get:Rule
    val mockWebserver = MockWebServer()

    @Before
    fun setUp(){
        dispatcher = NetworkFactory.provideMockWebServerDispatcher()
    }

    @Test
    fun getMeals_shouldReturnSuccessNetworkState_WhenMealExists(){
        runBlocking {
            //GIVEN
            mockWebserver.dispatcher = dispatcher

            //WHEN
            CUT = ViewModelFactory.providesMealsViewModel(mockWebserver)

            //THEN
            CUT.mealNetworkState.test {
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Loading::class.java)
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Success::class.java)
            }

        }
    }

    @Test
    fun getMeals_shouldReturnSuccessNetworkState_WhenMealDoesNotExist(){
        runBlocking {
            //GIVEN
            mockWebserver.dispatcher = dispatcher

            //WHEN
            CUT = ViewModelFactory.providesIncorrectMealsViewModel(mockWebserver)

            //THEN
            CUT.mealNetworkState.test {
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Loading::class.java)
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Error::class.java)
            }
        }

    }
}