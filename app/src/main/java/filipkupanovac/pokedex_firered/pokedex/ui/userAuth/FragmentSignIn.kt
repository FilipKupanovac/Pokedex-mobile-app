package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentSignInBinding

class FragmentSignIn : Fragment() {

    lateinit var binding : FragmentSignInBinding
    private val isUserSignedIn = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*if(isUserSignedIn){
            NavigateToPokedex()
        }*/

        binding = FragmentSignInBinding.inflate(
            inflater, container, false
        )

        //Here you put fragment initialization
        binding.buttonSignIn.setOnClickListener {
            TrySignIn()
        }
        binding.textViewNavigateToRegister.setOnClickListener{
            NavigateToRegister()
        }
        //End initialization
        return binding.root
    }

    private fun TrySignIn() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if(email.contains("@") && password.length > 7){
            Toast.makeText(activity, "Sign In successful, you are being redirected to Kanto Region!", Toast.LENGTH_LONG).show()
            NavigateToPokedex()
        }
    }

    private fun NavigateToPokedex() {
        val action = FragmentSignInDirections.actionFragmentSignInToPokedexMainHolder()
        findNavController().navigate(action)
    }

    private fun NavigateToRegister() {
        val action = FragmentSignInDirections.actionFragmentSignInToFragmentRegister()
        findNavController().navigate(action)
    }

    companion object{
        val TAG = "SignInFragment"

        fun create(): Fragment{
            return FragmentSignIn()
        }
    }
}