package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import filipkupanovac.pokedex_firered.pokedex.data.EMPTY_STRING
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.repositories.FirebaseAuthRepository
import filipkupanovac.pokedex_firered.pokedex.repositories.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val prefsManager: SharedPreferenceManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _isUserSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedIn: LiveData<Boolean>
        get() = _isUserSignedIn

    private val _currentFirebaseUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    val currentFirebaseUser: LiveData<FirebaseUser>
        get() = _currentFirebaseUser

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = firebaseAuthRepository.authenticateUser(email, password)
            _isUserSignedIn.postValue(currentUser != null)
            if (currentUser != null) {
                currentUser.displayName?.let { prefsManager.saveUser(it) }
                currentUser.email?.let {prefsManager.saveUserEmail(it)}
            }
            firestoreRepository.getUserFavorites{
                prefsManager.saveFavorites(it)
            }
        }
    }

    private val TAG = "SignInViewModel"

    fun isUserSignedIn(): Boolean = prefsManager.getUser() != EMPTY_STRING
}