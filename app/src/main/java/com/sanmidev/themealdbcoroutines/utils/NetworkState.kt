package com.sanmidev.themealdbcoroutines.utils

import androidx.annotation.StringRes

sealed class NetworkState<out T> {
    object NotFired : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
    class Success<T>(val data : T) :NetworkState<T>()
    class Error(@StringRes val errorStringId : Int, val throwable: Throwable) : NetworkState<Nothing>()

    override fun toString(): String {
       return when(this){
           Loading -> "Loading"
           is Success -> "Success[data=$data]"
           is Error -> "Error[throwable=$throwable]"
           NotFired -> "Api has not been called."
       }
    }
}
