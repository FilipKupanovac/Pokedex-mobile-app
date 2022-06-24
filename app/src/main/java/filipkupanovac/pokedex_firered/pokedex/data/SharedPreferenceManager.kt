package filipkupanovac.pokedex_firered.pokedex.data

import android.content.Context
import filipkupanovac.pokedex_firered.pokedex.PokedexApp
import java.lang.StringBuilder

class SharedPreferenceManager(
    private val context: Context
) {
    private val sharedPrefs =
        PokedexApp.application.getSharedPreferences("POKEDEX_SHARED_PREFS", Context.MODE_PRIVATE)
    private val editor = sharedPrefs.edit()

    fun saveUser(user: String) {
        //TODO(after firebase authentication get user from firebase to put here. it is important for signin because there user doesn't type his username)
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

        val updatedFavorites: String = setArrayToString(favorites)

        editor.putString(FAVORITE, updatedFavorites)
    }

    fun getFavorites(): String {
        return sharedPrefs.getString(FAVORITE, EMPTY_STRING).toString()
    }

    private fun setArrayToString(favorites: MutableList<Int>): String {
        val updatedFavorites = StringBuilder()
        favorites.forEach {
            updatedFavorites.append(it)
            updatedFavorites.append(",")
        }
        updatedFavorites.deleteRange(
            updatedFavorites.count() - 1,
            updatedFavorites.count()
        ) //remove trailing comma
        return updatedFavorites.toString()
    }

    private fun toggleFavorite(id: Int, favorites: MutableList<Int>) {
        if (favorites.contains(id)) {
            favorites.remove(id)
        } else {
            favorites.add(id)
            favorites.sort()
        }
    }
}

const val USER_ID = "userId"
const val SEARCHBAR_VALUE = "searchbarValue"
const val FAVORITE = "favorite"
const val EMPTY_STRING = ""