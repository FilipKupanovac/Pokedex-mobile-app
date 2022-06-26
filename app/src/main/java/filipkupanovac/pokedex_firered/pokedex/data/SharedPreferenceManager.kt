package filipkupanovac.pokedex_firered.pokedex.data

import android.content.Context
import filipkupanovac.pokedex_firered.pokedex.PokedexApp
import filipkupanovac.pokedex_firered.pokedex.helpers.parseFavoritesListToString
import java.lang.StringBuilder

class SharedPreferenceManager(
    /*private val context: Context*/
) {
    private val sharedPrefs =
        PokedexApp.application.getSharedPreferences("POKEDEX_SHARED_PREFS", Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    fun saveUser(user: String) {
        editor.putString(USER_ID, user)
        editor.apply()
    }

    fun getUser(): String {
        return sharedPrefs.getString(USER_ID, EMPTY_STRING).toString()
    }

    fun saveSearchbarValue(searchValue: String) {
        editor.putString(SEARCHBAR_VALUE, searchValue)
        editor.apply()
    }

    fun getSearchbarValue(): String {
        return sharedPrefs.getString(SEARCHBAR_VALUE, EMPTY_STRING).toString()
    }

    fun saveFavoritePokemonId(id: Int) {
        val currentFavorites = sharedPrefs.getString(FAVORITE, "")
        val favoritesArray = currentFavorites?.split(',')
        val favorites: MutableList<Int> = mutableListOf()
        favoritesArray?.forEach {
            favorites.add(it.toInt())
        }

        toggleFavorite(id, favorites)

        val updatedFavorites: String = parseFavoritesListToString(favorites)

        editor.putString(FAVORITE, updatedFavorites)
    }

    fun saveFavorites(favorites: String) {
        editor.putString(FAVORITE, favorites)
        editor.apply()
    }

    fun getFavorites(): String {
        return sharedPrefs.getString(FAVORITE, EMPTY_STRING).toString()
    }

    private fun toggleFavorite(id: Int, favorites: MutableList<Int>) {
        if (favorites.contains(id)) {
            favorites.remove(id)
        } else {
            favorites.add(id)
            favorites.sort()
        }
    }

    fun saveUserEmail(email: String) {
        editor.putString(EMAIL, email)
        editor.apply()
    }

    fun getUserEmail(): String {
        return sharedPrefs.getString(EMAIL, EMPTY_STRING).toString()
    }
}

const val USER_ID = "userId"
const val EMAIL = "email"
const val SEARCHBAR_VALUE = "searchbarValue"
const val FAVORITE = "favorite"
const val EMPTY_STRING = ""