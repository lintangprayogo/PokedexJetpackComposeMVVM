package com.lintang.pokedexjetpackcomposemvvm.data.models

import androidx.compose.ui.graphics.Color


data class PokeEntry(val name: String, val imageUrl: String, val number: Int,var dominantColor: Color?=null)
