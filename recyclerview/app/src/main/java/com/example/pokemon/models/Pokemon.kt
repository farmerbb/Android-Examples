package com.example.pokemon.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pokemon {

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("number")
    @Expose
    var number: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("types")
    @Expose
    var types: List<String>? = null

    @SerializedName("evolutions")
    @Expose
    var evolutions: List<Pokemon>? = null

    fun getTypes(): String {
        val localTypes: List<String> = types ?: return ""

        if(localTypes.size == 1)
            return localTypes.first()

        return "${localTypes.first()}/${localTypes.last()}"
    }
}
