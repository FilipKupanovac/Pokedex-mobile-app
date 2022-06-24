package filipkupanovac.pokedex_firered.pokedex.ui.profileInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.EMPTY_STRING
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileInfoViewModel(private val prefsManager: SharedPreferenceManager) : ViewModel() {
    //private val _username: MutableLiveData<String> = MutableLiveData()
    //val username: LiveData<String>
    //    get() = _username

    fun getUsername() : String {
        /*viewModelScope.launch(Dispatchers.IO) {
            _username.postValue(prefsManager.getUser())
        }*/
        return prefsManager.getUser()
    }

    fun signOutUser(){
        prefsManager.saveUser(EMPTY_STRING)
    }
}