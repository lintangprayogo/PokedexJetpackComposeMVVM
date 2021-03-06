package com.lintang.pokedexjetpackcomposemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.lintang.pokedexjetpackcomposemvvm.pokedetail.PokeDetailPage
import com.lintang.pokedexjetpackcomposemvvm.pokelist.PokeListPage
import com.lintang.pokedexjetpackcomposemvvm.ui.theme.PokedexJetpackComposeMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexJetpackComposeMVVMTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_page") {
                    composable("pokemon_list_page") {
                        PokeListPage(navController)
                    }
                    composable("pokemon_detail_page/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokeDetailPage(
                            name = pokemonName ?: "-",
                            dominantColor = dominantColor,
                            navController = navController
                        )
                    }
                }
            }
        }
    }


}

