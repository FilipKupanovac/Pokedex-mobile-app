package filipkupanovac.pokedex_firered.pokedex.ui.profileInfo

import androidx.lifecycle.ViewModel
import filipkupanovac.pokedex_firered.pokedex.data.EMPTY_STRING
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.repositories.FirestoreRepository

class ProfileInfoViewModel(
    private val prefsManager: SharedPreferenceManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    fun getUsername(): String {
        return prefsManager.getUser()
    }

    fun signOutUser() {
        prefsManager.saveUser(EMPTY_STRING)
        prefsManager.saveUserEmail(EMPTY_STRING)
        prefsManager.saveFavorites(EMPTY_STRING)
    }

    //TODO() ako bude vremena obrisi usera
}