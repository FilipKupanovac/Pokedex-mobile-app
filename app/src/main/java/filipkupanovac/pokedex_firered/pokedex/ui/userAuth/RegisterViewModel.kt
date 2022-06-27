package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.repositories.FirebaseAuthRepository
import filipkupanovac.pokedex_firered.pokedex.repositories.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val prefsManager: SharedPreferenceManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _isUserRegistered: MutableLiveData<Boolean> = MutableLiveData()
    val isUserRegistered: LiveData<Boolean>
        get() = _isUserRegistered

    fun register(
        email: String,
        username: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseAuthRepository.createNewUser(email, username, password) {
                _isUserRegistered.postValue(it)
            }
            firestoreRepository.addNewUserFavorites(email)
            prefsManager.saveUser(username)
            prefsManager.saveUserEmail(email)
        }
    }
}