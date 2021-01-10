package com.sanmidev.themealdbcoroutines.features.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmidev.themealdbcoroutines.R
import com.sanmidev.themealdbcoroutines.data.model.category.CategoryModel
import com.sanmidev.themealdbcoroutines.data.repo.MealsRepository
import com.sanmidev.themealdbcoroutines.utils.NetworkState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel @ViewModelInject constructor(private val mealsRepository: MealsRepository) :
    ViewModel() {

    private val _getCategoriesNetworkState =
        MutableStateFlow<NetworkState<List<CategoryModel>>>(NetworkState.Initial)
    val getCategoriesNetworkState
        get() = _getCategoriesNetworkState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _getCategoriesNetworkState.value = NetworkState.Loading

            try {
                val response = mealsRepository.getCategories()

                _getCategoriesNetworkState.value = NetworkState.Success(response)

            } catch (cancellationEx: CancellationException) {
                throw  cancellationEx
            } catch (ex: Exception) {
                _getCategoriesNetworkState.value = NetworkState.Error(R.string.meal_error, ex)
            }
        }
    }
}