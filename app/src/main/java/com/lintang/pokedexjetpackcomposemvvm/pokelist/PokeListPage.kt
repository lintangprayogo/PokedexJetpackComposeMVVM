package com.lintang.pokedexjetpackcomposemvvm.pokelist


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lintang.pokedexjetpackcomposemvvm.R


@Composable
fun PokeListPage(navController: NavController) {
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column {

            Spacer(modifier = Modifier.height(20.dp))
            Row(Modifier.align(CenterHorizontally)
            ,verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pokeball),
                    contentDescription = "LOGO",
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                       
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Pokedex" ,style = TextStyle(color = Color.Black,fontSize = 24.sp))
            }
            
            
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(CenterHorizontally), hint = "Type.."
            ){

            }
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

        if (isHintDisplayed){
            Text(text = hint,color = Color.LightGray,modifier =Modifier.padding(horizontal = 20.dp, vertical = 12.dp) )

        }

    }

}