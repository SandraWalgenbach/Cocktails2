package com.example.cocktails2.remote.models

data class Cocktail (
    val id: String,
    val name: String,
    val alcoholic: Boolean,
    val glass: String,
    val instruction: String,
    val ingredients: List<Map<String, String>>,
    val image: String
)