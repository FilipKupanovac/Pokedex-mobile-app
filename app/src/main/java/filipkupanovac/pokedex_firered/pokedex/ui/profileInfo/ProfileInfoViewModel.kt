package filipkupanovac.pokedex_firered.pokedex.ui.profileInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.EMPTY_STRING
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.repositories.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
}