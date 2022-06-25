package filipkupanovac.pokedex_firered.pokedex.repositories

import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import filipkupanovac.pokedex_firered.pokedex.PokedexApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun authenticateUser(email: String, password: String): FirebaseUser? {
        var currentUser: FirebaseUser? = null
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                currentUser = it.user
            }
                .addOnFailureListener {

                }.await()
        } catch (e: Exception) {
            //Toast.makeText(PokedexApp.application, e.message.toString(), Toast.LENGTH_LONG).show()
            Log.d("FIREBASE AUTH REPO", "authenticateUser: ${e.message.toString()}")
        }
        return currentUser
    }

    suspend fun createNewUser(email: String, username: String, password: String, onResult: (Boolean) -> Unit)/*: Boolean*/ {
        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        Toast.makeText(
                            PokedexApp.application,
                            "Successfully registered. You are being redirected to Kanto region!",
                            Toast.LENGTH_SHORT
                        ).show()

                        setUsername(username)
                        onResult(it.isSuccessful)
                    }
                    .addOnFailureListener {
                        onResult(false)
                    }.await()
            } catch (e: Exception) {
                onResult(false)
                Toast.makeText(PokedexApp.application, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUsername(username: String) {
        val firebaseUser = getCurrentUser()

        val profileUpdates: UserProfileChangeRequest =
            UserProfileChangeRequest.Builder().setDisplayName(username).build()
        firebaseUser?.updateProfile(profileUpdates)
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun deleteUser() {
        firebaseAuth.currentUser?.delete()?.addOnCompleteListener { it ->
            if (it.isSuccessful) {
                Toast.makeText(PokedexApp.application, "Deleted account", Toast.LENGTH_SHORT)
            }
        }
    }
}