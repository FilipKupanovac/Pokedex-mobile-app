package filipkupanovac.pokedex_firered.pokedex.repositories

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
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
                                Log.d(TAG, "getUserFavorites SUCK: ${document.get("favorites")}")
                                onResult(document.get("favorites").toString())
                                sharedPreferenceManager.saveFavorites(
                                    document.get("favorites").toString()
                                )
                            }
                        }
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "getUserFavorites FAILURE: ${it.message.toString()}")
                    }
            } catch (e: Exception) {
                Log.d(TAG, "getUserFavorites: FAIL")
            }
        }
    }

    suspend fun saveFavoriteId(pokemonId: Int) {

    }

    suspend fun addNewUserFavorites(email: String){
        val userFavorites = hashMapOf(
            "email" to email,
            "favorites" to ""
        )
        firestore.collection("userFavorites").add(userFavorites)
    }

    companion object {
        private const val TAG = "FirestoreRepository"
    }
}