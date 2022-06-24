package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.EMPTY_STRING
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.di.prefsModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(/*private val ovdje_će_ići_firebase_repository_varijabla*/private val prefsManager: SharedPreferenceManager) : ViewModel() {

    private val _isUserSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isUserSignedIn: LiveData<Boolean>
        get() = _isUserSignedIn

    private val _currentUser: MutableLiveData<Int /*OVDJE_ĆE_IĆI FIREBASEUSER*/> = MutableLiveData()
    val currentUser: LiveData<Int/*OVDJE_ĆE_IĆI FIREBASEUSER*/>
        get() = _currentUser

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            /*
            * PRIJAVA USERA IZ BAZE
            *
            * Povratna vrijednost:
            *   user postoji -> promijeni live data na true
            *   ne postoji -> live data na false
            * */

            if (email.contains("@") && password.length > 3) {
                _isUserSignedIn.postValue(true)
                //TODO(ovdje pravog lika stavit)
                prefsManager.saveUser("ASH KEČAM")
            } else {
                _isUserSignedIn.postValue(false)
            }
        }
    }

    fun isUserSignedIn() : Boolean = prefsManager.getUser() != EMPTY_STRING
}