package com.lintang.pokedexjetpackcomposemvvm.util

sealed class Resource<T>(val data:T?=null,val throwable: Throwable?=null) {
    class Success<T>(data: T):Resource<T>(data=data)
    class Loading<T>(data: T?=null):Resource<T>(data =data)
    class Error<T>(throwable: Throwable?):Resource<T>(throwable = throwable)
}