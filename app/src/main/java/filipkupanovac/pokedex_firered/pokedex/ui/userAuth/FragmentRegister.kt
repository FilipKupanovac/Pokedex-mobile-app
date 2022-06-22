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
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentRegister : Fragment() {

    private val registerViewModel: RegisterViewModel by viewModel()
    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(
            inflater, container, false
        )

        setClickListeners()
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        registerViewModel.isUserRegistered.observe(viewLifecycleOwner) {
            if (it) {
                NavigateToPokedex()
            } else {
                Toast.makeText(activity, "Registration failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setClickListeners() {
        binding.buttonRegister.setOnClickListener {
            registerViewModel.register(
                binding.editTextEmail.text.toString(),
                binding.editTextUsername.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
        binding.textViewNavigateToSignIn.setOnClickListener {
            NavigateToSignIn()
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

    companion object {
        val TAG = "SignInFragment"

        fun create(): Fragment {
            return FragmentSignIn()
        }
    }
}