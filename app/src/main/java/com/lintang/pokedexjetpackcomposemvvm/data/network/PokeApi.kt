package com.lintang.pokedexjetpackcomposemvvm.data.network



import com.lintang.pokedexjetpackcomposemvvm.data.network.response.PokeList
import com.lintang.pokedexjetpackcomposemvvm.data.network.response.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemons(@Query("limit")limit:Int,@Query("offset")offset:Int ): PokeList


    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name")name:String ): Pokemon



}