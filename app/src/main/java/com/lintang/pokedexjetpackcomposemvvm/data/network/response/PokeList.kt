package com.lintang.pokedexjetpackcomposemvvm.data.network.response


data class PokeList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)