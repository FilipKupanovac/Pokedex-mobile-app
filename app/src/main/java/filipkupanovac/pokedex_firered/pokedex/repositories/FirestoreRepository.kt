package filipkupanovac.pokedex_firered.pokedex.repositories

import com.google.firebase.firestore.FirebaseFirestore
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.helpers.parseFavoritesListToString
import filipkupanovac.pokedex_firered.pokedex.helpers.parseFavoritesToListInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirestoreRepository(
    private val firestore: FirebaseFirestore,
    private val sharedPreferenceManager: SharedPreferenceManager
) {
    suspend fun getUserFavorites(onResult: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                firestore.collection("userFavorites").get()
                    .addOnCompleteListener { result ->
                        for (document in result.result) {
                            if (document.get("email")
                                    ?.equals(sharedPreferenceManager.getUserEmail()) == true
                            ) {
                                onResult(document.get("favorites").toString())
                                sharedPreferenceManager.saveFavorites(
                                    document.get("favorites").toString()
                                )
                            }
                        }
                    }
                    .addOnFailureListener {
                    }
            } catch (e: Exception) {
            }
        }
    }

    suspend fun saveFavoritePokemonId(pokemonId: Int) {
        withContext(Dispatchers.IO) {
            firestore.collection("userFavorites").get()
                .addOnCompleteListener { result ->
                    for (document in result.result) {
                        if (document.get("email")
                                ?.equals(sharedPreferenceManager.getUserEmail()) == true
                        ) {
                            val favoritesString = document.get("favorites").toString()
                            val favoritesList: MutableList<Int> = mutableListOf()
                            favoritesList.addAll(parseFavoritesToListInt(favoritesString))
                            toggleFavorite(pokemonId, favoritesList)

                            firestore.collection("userFavorites").document(document.id)
                                .update("favorites", parseFavoritesListToString(favoritesList))
                        }
                    }
                }
        }
    }

    private fun toggleFavorite(id: Int, favorites: MutableList<Int>) {
        if (favorites.contains(id)) {
            favorites.remove(id)
        } else {
            favorites.add(id)
            favorites.sort()
        }
    }

    fun addNewUserFavorites(email: String) {
        val userFavorites = hashMapOf(
            "email" to email,
            "favorites" to ""
        )
        firestore.collection("userFavorites").add(userFavorites)
    }

}