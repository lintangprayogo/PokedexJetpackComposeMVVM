package com.lintang.pokedexjetpackcomposemvvm.data.network.response


import com.google.gson.annotations.SerializedName

data class GameIndice(
    @SerializedName("game_index")
    val gameIndex: Int,
    val version: Version
)