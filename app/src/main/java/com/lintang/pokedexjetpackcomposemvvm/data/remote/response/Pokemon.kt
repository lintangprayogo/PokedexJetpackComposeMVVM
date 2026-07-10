package com.lintang.pokedexjetpackcomposemvvm.data.remote.response


data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val stats: List<Stat>,
    val sprites: Sprites?
)
