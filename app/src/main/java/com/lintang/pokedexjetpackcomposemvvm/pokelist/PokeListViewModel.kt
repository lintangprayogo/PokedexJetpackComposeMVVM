package com.lintang.pokedexjetpackcomposemvvm.pokelist


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(val repo: PokeRepo) : ViewModel() {

   private  var curPage=0
    var pokeList= mutableStateOf<List<PokeEntry>>(listOf())
    var loadError = mutableStateOf("")
    var  isLoad   = mutableStateOf(false)
    var isEnd = mutableStateOf(false)

   init {
       getPokemons()
   }

    fun  getPokemons()= viewModelScope.launch {
        isLoad.value=true
        val result =repo.getPokemons(PAGE_SIZE,curPage)
        when(result){
            is  Resource.Error->{
                loadError.value=result.throwable?.localizedMessage?:"Unknown Error"
                isLoad.value=false
            }


            is  Resource.Success->{
                val data= result.data
                var number=1

                if(data!=null){
                    val entries =data.results.mapIndexed { index, item ->
                        val number=if(item.url.endsWith("/")){
                            item.url.dropLast(1).takeLastWhile { it.isDigit() }
                        }else {
                            item.url.takeLastWhile { it.isDigit() }
                        }
                        val imgUrl="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokeEntry(item.name,imgUrl,number.toInt())
                    }
                    curPage++
                    loadError.value=""
                    isLoad.value=false
                    pokeList.value=entries
                }
                else {
                    loadError.value="Unknown Error"
                    isLoad.value=false
                }
            }
        }

    }
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}