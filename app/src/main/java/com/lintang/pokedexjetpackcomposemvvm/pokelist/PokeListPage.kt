package com.lintang.pokedexjetpackcomposemvvm.pokelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

import com.lintang.pokedexjetpackcomposemvvm.R
import com.lintang.pokedexjetpackcomposemvvm.data.models.PokeEntry
import com.lintang.pokedexjetpackcomposemvvm.ui.theme.RobotoCondensed


@Composable
fun PokeListPage(navController: NavController) {


    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column {

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                Modifier.align(CenterHorizontally), verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pokeball),
                    contentDescription = "LOGO",
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)

                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Pokedex", style = TextStyle(color = Color.Black, fontSize = 24.sp))
            }


            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(CenterHorizontally), hint = "Type.."
            ) {
            
                
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokeList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier, hint: String, onSearch: (String) -> Unit = {}) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier) {
        BasicTextField(
            value = text, onValueChange = {
                text = it
                onSearch(it)
            }, maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                },
            textStyle = TextStyle(color = Color.Black)
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

        }

    }

}

@Composable
fun PokeList(  navController: NavController,viewModel: PokeListViewModel= hiltViewModel()){

    val  entries by remember {viewModel.pokeList}
    val  isEnd  by remember {viewModel.isEnd}
    val  isLoad by remember { viewModel.isLoad }
    val  loadError by remember {viewModel.loadError}

    LazyColumn(contentPadding = PaddingValues(16.dp)){
          val itemCount = if(entries.size%2==0){
              entries.size/2
          }else {
              entries.size/2+1
          }
        items (itemCount){
            if(it >=itemCount-1 && !isEnd){
                viewModel.getPokemons()
            }
            PokedexContent(rowIndex = it, entries = entries , navController =navController)
        }


    }
}

@ExperimentalCoilApi
@Composable
fun PokedexEntry(
    pokeEntry: PokeEntry,
    navController: NavController,
    modifier: Modifier,
    viewModel: PokeListViewModel = hiltViewModel()
) {

    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate("pokemon_detail_page/${defaultDominantColor.toArgb()}/${pokeEntry.name}")
            }
    ) {
        Column {

            Image(
                painter = rememberImagePainter(
                    data = pokeEntry.imageUrl,
                    builder = {
                        crossfade(true)
                    }

                ),

                contentDescription = pokeEntry.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)

            )

            Text(
                text = pokeEntry.name, fontFamily = RobotoCondensed, fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}


@Composable
fun PokedexContent(rowIndex:Int,entries:List< PokeEntry>,navController: NavController){
    Column {
        Row {
            PokedexEntry(
                pokeEntry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= rowIndex * 2 + 2) {
                PokedexEntry(
                    pokeEntry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

}