package com.example.pokemon.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonJsonResponse {

    @SerializedName("pokemon")
    @Expose
    var pokemon: List<Pokemon>? = null

}
