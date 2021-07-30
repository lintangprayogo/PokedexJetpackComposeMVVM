package com.lintang.pokedexjetpackcomposemvvm.repo

import com.lintang.pokedexjetpackcomposemvvm.data.network.PokeApi
import com.lintang.pokedexjetpackcomposemvvm.data.network.response.PokeList
import com.lintang.pokedexjetpackcomposemvvm.data.network.response.Pokemon
import com.lintang.pokedexjetpackcomposemvvm.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class PokeRepo  @Inject constructor(private  val pokeApi: PokeApi) {

suspend fun  getPokemons(limit:Int,offset:Int):Resource<PokeList> =
    try {
       val data= pokeApi.getPokemons(limit = limit,offset = offset)
        Resource.Success(data)
    }catch (throwable:Throwable){
        Resource.Error<PokeList>(throwable)
    }


    suspend fun  getPokemonByName(name:String):Resource<Pokemon> =
        try {
            val data= pokeApi.getPokemonByName(name)
            Resource.Success(data)
        }catch (throwable:Throwable){
            Resource.Error(throwable)
        }

}