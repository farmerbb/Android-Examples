package com.example.pokemon.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.example.pokemon.models.Pokemon
import com.example.pokemon.models.PokemonJsonResponse
import java.io.InputStreamReader

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    fun getPokemon(): List<Pokemon> {
        val inputStream = app.assets.open("pokemon.json")
        val reader = InputStreamReader(inputStream)
        val response = Gson().fromJson<PokemonJsonResponse>(reader, PokemonJsonResponse::class.java)

        return response?.pokemon.orEmpty()
    }
}
