package com.sanmidev.themealdbcoroutines.features.meal


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sanmidev.themealdbcoroutines.R
import com.sanmidev.themealdbcoroutines.data.model.meal.MealModel
import com.sanmidev.themealdbcoroutines.data.repo.MealsDBRepository
import com.sanmidev.themealdbcoroutines.utils.NetworkState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MealsViewModel @AssistedInject constructor(
    private val mealsDBRepository: MealsDBRepository,
    @Assisted private val mealName: String
) : ViewModel() {

    private val _mealsNetworkState =
        MutableStateFlow<NetworkState<List<MealModel>>>(NetworkState.Initial)

    val mealNetworkState get() = _mealsNetworkState.asStateFlow()

    init {

        //TODO [OA_03-01-2020] Update this code to use kotlin result class. It should be a custom runcathcing as the current one breaks the coroutine cancellation
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _mealsNetworkState.value = NetworkState.Loading

                val response = mealsDBRepository.getMeal(mealName)

                _mealsNetworkState.value = NetworkState.Success(response)

            } catch (cancellationEx: CancellationException) {
                throw  cancellationEx
            } catch (ex: Exception) {
                _mealsNetworkState.value = NetworkState.Error(R.string.meal_error, ex)
            }
        }

    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(mealName: String): MealsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            mealName: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(mealName) as T
            }
        }
    }

}