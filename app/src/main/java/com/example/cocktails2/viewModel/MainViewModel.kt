package com.example.cocktails2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails2.remote.ApiRepository
import com.example.cocktails2.remote.FirebaseRepository
import com.example.cocktails2.remote.models.Cocktail
import com.example.cocktails2.remote.models.CocktailMap
import com.example.cocktails2.remote.models.CocktailPreview
import com.example.cocktails2.remote.models.User
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val apiRepository = ApiRepository.getInstance()
    private val firebaseRepository = FirebaseRepository.getInstance()

    private val _cocktailList: MutableLiveData<List<CocktailPreview>> = MutableLiveData(emptyList())
    val cocktailList: LiveData<List<CocktailPreview>> = _cocktailList

    private val _cocktailDetails: MutableLiveData<Cocktail?> = MutableLiveData(null)
    val cocktailDetails: LiveData<Cocktail?> = _cocktailDetails

    val user: LiveData<User?>
        get() = firebaseRepository.user

    fun getCocktailList() {
        viewModelScope.launch {
            _cocktailList.value = apiRepository.getAllCocktails().drinks
        }
    }

    fun getCocktailDetails(id: String) {
        viewModelScope.launch {
            val cocktailMap = apiRepository.getDrinkDetails(id)
            _cocktailDetails.value = transformCocktailMap(cocktailMap)
            //println(_cocktailDetails.value)
            //_cocktailDetails.postValue(transformCocktailMap(cocktailMap))
        }
    }

    fun checkAuth() {
        firebaseRepository.checkAuth()
    }

    fun login(email: String, password: String) {
        firebaseRepository.login(email, password)
    }

    fun register(email: String, password: String, username: String) {
        firebaseRepository.register(email, password, username)
    }

    fun updateUsername(newUsername: String, id: String) {
        firebaseRepository.updateUsername(newUsername, id)
    }

    fun logout() {
        firebaseRepository.logout()
    }

    //erstellt eine map mit bis zu 15 zutaten pro cocktail
    private fun transformCocktailMap(cocktailMap: CocktailMap): Cocktail {
        val cocktail = cocktailMap.drinks.map { drink ->
            Cocktail(
                id = drink["idDrink"] ?: "",
                name = drink["strDrink"] ?: "",
                alcoholic = drink["strAlcoholic"] == "Alcoholic",
                glass = drink["strGlass"] ?: "",
                instruction = drink["strInstructions"] ?: "",
                ingredients = listOf(
                    mapOf(
                        "name" to (drink["strIngredient1"] ?: ""),
                        "measure" to (drink["strMeasure1"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient2"] ?: ""),
                        "measure" to (drink["strMeasure2"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient3"] ?: ""),
                        "measure" to (drink["strMeasure3"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient4"] ?: ""),
                        "measure" to (drink["strMeasure4"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient5"] ?: ""),
                        "measure" to (drink["strMeasure5"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient6"] ?: ""),
                        "measure" to (drink["strMeasure6"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient7"] ?: ""),
                        "measure" to (drink["strMeasure7"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient8"] ?: ""),
                        "measure" to (drink["strMeasure8"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient9"] ?: ""),
                        "measure" to (drink["strMeasure9"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient10"] ?: ""),
                        "measure" to (drink["strMeasure10"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient11"] ?: ""),
                        "measure" to (drink["strMeasure11"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient12"] ?: ""),
                        "measure" to (drink["strMeasure12"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient13"] ?: ""),
                        "measure" to (drink["strMeasure13"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient14"] ?: ""),
                        "measure" to (drink["strMeasure14"] ?: "")
                    ),
                    mapOf(
                        "name" to (drink["strIngredient15"] ?: ""),
                        "measure" to (drink["strMeasure15"] ?: "")
                    )
                    //geändert
                ).filter { it["name"]?.isNotEmpty() ?: false },
                image = drink["strDrinkThumb"] ?: ""
            )
        }.first()

        return cocktail
    }
}