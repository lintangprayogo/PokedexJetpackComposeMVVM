package com.lintang.pokedexjetpackcomposemvvm.data.remote.response


import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatX
)