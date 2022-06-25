package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class RegisterViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val prefsManager: SharedPreferenceManager
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
            Log.d(TAG, "register: UMRI JEBOTE2")
            firebaseAuthRepository.createNewUser(email, username, password){
                _isUserRegistered.postValue(it)
            }
            prefsManager.saveUser(username)
        }
    }
    private val TAG = "RegisterViewModel"
}