package com.sanmidev.themealdbcoroutines.features.categories

import androidx.lifecycle.asLiveData
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.sanmidev.themealdbcoroutines.NetworkFactory
import com.sanmidev.themealdbcoroutines.NetworkFactory.provideErrorResponse
import com.sanmidev.themealdbcoroutines.ViewModelFactory
import com.sanmidev.themealdbcoroutines.utils.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class CategoriesViewModelTest {

    private lateinit var CUT: CategoriesViewModel

    private lateinit var dispatcher: Dispatcher

    @get:Rule
    val mockWebServer = MockWebServer()


    @Before
    fun setUp() {
        dispatcher = NetworkFactory.provideMockWebServerDispatcher()
    }


    @Test
    fun getCategories_ShouldReturnSuccessNetworkState_whenRequestIsSuccessful() {
        runBlocking {
            //GIVEN
            mockWebServer.dispatcher = dispatcher

            //WHEN
            CUT = ViewModelFactory.providesCategoriesViewModel(mockWebServer)

            //THEN
            CUT.getCategoriesNetworkState.test {
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Loading::class.java)
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Success::class.java)
            }

        }
    }

    @Test
    fun getCategories_ShouldReturnErrorNetworkState_whenRequestFails() {
        runBlocking {
            //GIVEN
            mockWebServer.provideErrorResponse()

            //WHEN
            CUT = ViewModelFactory.providesCategoriesViewModel(mockWebServer)

            //THEN
            CUT.getCategoriesNetworkState.test {
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Loading::class.java)
                Truth.assertThat(this.expectItem()).isInstanceOf(NetworkState.Error::class.java)
            }
        }
    }


    @After
    fun tearDown() {
    }
}