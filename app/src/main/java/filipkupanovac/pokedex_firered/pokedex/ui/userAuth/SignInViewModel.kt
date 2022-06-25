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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val firebaseAuthRepository: FirebaseAuthRepository, private val prefsManager: SharedPreferenceManager) : ViewModel() {

    private val _isUserSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedIn: LiveData<Boolean>
        get() = _isUserSignedIn

    /*private val _currentUser: MutableLiveData<Int *//*OVDJE_ĆE_IĆI FIREBASEUSER*//*> = MutableLiveData()
    val currentUser: LiveData<Int*//*OVDJE_ĆE_IĆI FIREBASEUSER*//*>
        get() = _currentUser*/

    private val _currentFirebaseUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    val currentFirebaseUser: LiveData<FirebaseUser>
        get() = _currentFirebaseUser

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            /*
            * PRIJAVA USERA IZ BAZE
            *
            * Povratna vrijednost:
            *   user postoji -> promijeni live data na true
            *   ne postoji -> live data na false
            * */

            val currentUser = firebaseAuthRepository.authenticateUser(email,password)
            _isUserSignedIn.postValue(currentUser!=null)
            if(currentUser != null){
                Log.d(TAG, "signIn: ${currentUser.displayName}")
                currentUser.displayName?.let { prefsManager.saveUser(it) }
            }
        }
    }
    private val TAG = "SignInViewModel"

    fun isUserSignedIn() : Boolean = prefsManager.getUser() != EMPTY_STRING
}