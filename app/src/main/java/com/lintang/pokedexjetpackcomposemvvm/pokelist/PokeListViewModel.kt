package com.lintang.pokedexjetpackcomposemvvm.pokelist


import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.lintang.pokedexjetpackcomposemvvm.data.models.PokeEntry
import com.lintang.pokedexjetpackcomposemvvm.repo.PokeRepo
import com.lintang.pokedexjetpackcomposemvvm.util.Constant.PAGE_SIZE
import com.lintang.pokedexjetpackcomposemvvm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(private val repo: PokeRepo) : ViewModel() {

    private  var curPage=0
    var pokeList= mutableStateOf<List<PokeEntry>>(listOf())
    var loadError = mutableStateOf("")
    var  isLoad   = mutableStateOf(false)
    var isEnd = mutableStateOf(false)

    private var cachedList = listOf<PokeEntry>()
    private var isSearchStarting= true
    var isSearching= mutableStateOf(false)

   init {
       getPokemons()
   }

    fun searchPokemon(query:String)=viewModelScope.launch {
             val listOfSearch=if(isSearchStarting){
                 pokeList.value
             }else {
                 cachedList
             }

         viewModelScope.launch(Dispatchers.Default) {
            val  results = listOfSearch.filter {
                it.name.contains(query.trim(),ignoreCase = true)||it.number.toString()==query
            }
             if(isSearchStarting){
                 cachedList=pokeList.value
                 isSearchStarting=false
             }

             pokeList.value=results
             isSearching.value=true
         }



    }
    fun  getPokemons()= viewModelScope.launch {
        isLoad.value=true
        val result =repo.getPokemons(limit = PAGE_SIZE,offset = curPage)
        when(result){
            is  Resource.Error->{
                loadError.value=result.throwable?.localizedMessage?:"Unknown Error"
                isLoad.value=false
            }

            is  Resource.Success->{
                val data= result.data
                if(data!=null){
                    isEnd.value = curPage * PAGE_SIZE >= result.data.count
                    val entries =data.results.mapIndexed { _, item ->
                        val number=if(item.url.endsWith("/")){
                            item.url.dropLast(1).takeLastWhile { it.isDigit() }
                        }else {
                            item.url.takeLastWhile { it.isDigit() }
                        }
                        val imgUrl="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png"
                        PokeEntry(item.name,imgUrl,number.toInt())
                    }
                    curPage+= PAGE_SIZE
                    loadError.value=""
                    isLoad.value=false
                    pokeList.value+=entries

                }
                else {
                    loadError.value="Unknown Error"
                    isLoad.value=false
                }
            }
            else -> {

            }
        }

    }
    fun calcDominantColor(bitmap: Bitmap, onFinish: (Color) -> Unit) {
        val bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}