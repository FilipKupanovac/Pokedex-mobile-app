package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentRegisterBinding

class FragmentRegister  : Fragment() {

    lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(
            inflater, container, false
        )

        //Here you put fragment initialization
        binding.buttonRegister.setOnClickListener {
            TryRegister()
        }
        binding.textViewNavigateToSignIn.setOnClickListener {
            NavigateToSignIn()
        }

        //End initialization
        return binding.root
    }

    private fun TryRegister() {
        val email = binding.editTextEmail.text.toString()
        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.contains("@") && username.isNotBlank() && password.length > 7){
            Log.d("TRYREGISTER", "successful registration")
            Toast.makeText(activity,"Successful registration, you are being redirected to Kanto region", Toast.LENGTH_LONG).show()

            NavigateToPokedex()
        }
    }

    private fun NavigateToPokedex() {
        val action = FragmentRegisterDirections.actionFragmentRegisterToPokedexMainHolder()
        findNavController().navigate(action)
    }

    private fun NavigateToSignIn() {
        val action = FragmentRegisterDirections.actionFragmentRegisterToFragmentSignIn()
        findNavController().navigate(action)
    }

    companion object{
        val TAG = "SignInFragment"

        fun create(): Fragment {
            return FragmentSignIn()
        }
    }
}