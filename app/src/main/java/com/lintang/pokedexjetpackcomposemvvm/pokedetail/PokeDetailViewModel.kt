package com.lintang.pokedexjetpackcomposemvvm.pokedetail

import androidx.lifecycle.ViewModel
import com.lintang.pokedexjetpackcomposemvvm.repo.PokeRepo
import com.lintang.pokedexjetpackcomposemvvm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel @Inject constructor(val pokeRepo: PokeRepo) : ViewModel() {

    suspend fun getPokemonByName(name: String) =
        try {
            pokeRepo.getPokemonByName(name)
        } catch (throwable: Throwable) {
            Resource.Error(throwable = throwable)
        }

}