package com.example.cocktails2.remote.models

data class Cocktail (
    val id: String,
    val name: String,
    val alcoholic: Boolean,
    val glass: String,
    val instruction: String,
    val ingredients: List<Map<String, String>>,
    val image: String
) {
    //hinzugefügt
    fun ingredientsAsString(): String {
        val sb = StringBuilder()
        for (map in ingredients) {
            for ((key, value) in map) {
                sb.append("$key: $value\n")
            }
        }
        return sb.toString().trim()
    }
}