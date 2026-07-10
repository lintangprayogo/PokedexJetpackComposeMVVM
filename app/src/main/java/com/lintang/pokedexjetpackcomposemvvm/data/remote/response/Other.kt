package com.lintang.pokedexjetpackcomposemvvm.data.remote.response


import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)
