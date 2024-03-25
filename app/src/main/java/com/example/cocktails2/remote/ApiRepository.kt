package com.example.cocktails2.remote

import com.example.cocktails2.remote.api.DrinkApi
import com.example.cocktails2.remote.models.ApiResponse
import com.example.cocktails2.remote.models.CocktailMap

//constructor und companion object sorgen dafür das immer das selbe aufgerufen wird (mit hilfe erstellt)
class ApiRepository private constructor()  {

    private val apiService = DrinkApi

    suspend fun getAllCocktails(): ApiResponse {
        println("GETTING LIST OF COCKTAILS")
        return apiService.retrofitService.getDrinkList()
    }

    suspend fun getDrinkDetails(id: String): CocktailMap {
        return apiService.retrofitService.getDrinkDetails(id)
    }

    companion object {
        @Volatile private var instance: ApiRepository? = null

        fun getInstance(): ApiRepository {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ApiRepository()
                    }
                }
            }
            return instance!!
        }
    }
}