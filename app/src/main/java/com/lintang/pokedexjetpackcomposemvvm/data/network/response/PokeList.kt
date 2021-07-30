package com.lintang.pokedexjetpackcomposemvvm.data.network.response


import com.google.gson.annotations.SerializedName

data class PokeList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)