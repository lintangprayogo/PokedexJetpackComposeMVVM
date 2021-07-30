package com.lintang.pokedexjetpackcomposemvvm.data.network.response


import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)