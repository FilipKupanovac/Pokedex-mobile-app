package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import kotlin.coroutines.coroutineContext

class RegisterViewModel(private val prefsManager: SharedPreferenceManager) : ViewModel() {

    private val MINIMUM_PASSWORD_LENGTH = 4

    private val _isUserRegistered: MutableLiveData<Boolean> = MutableLiveData()
    val isUserRegistered: LiveData<Boolean>
        get() = _isUserRegistered

    fun register(
        email: String,
        username: String,
        password: String
    ) {
        if (password.length < MINIMUM_PASSWORD_LENGTH) {
            _isUserRegistered.postValue(false)
        }
        if (email.contains("@") && username.isNotBlank()) {
            //POKUŠAJ REGISTRACIJE NA PRAVI SERVIS
            _isUserRegistered.postValue(true)
            prefsManager.saveUser(username)
        }

    }
}