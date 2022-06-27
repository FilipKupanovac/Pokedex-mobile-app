package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentRegister : Fragment() {

    private val registerViewModel: RegisterViewModel by viewModel()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                navigateToPokedex()
            }
        }
    }

    private fun setClickListeners() {
        binding.buttonRegister.setOnClickListener {
            registerViewModel.register(
                binding.editTextEmail.text.toString(),
                binding.editTextUsername.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
        binding.textViewNavigateToSignIn.setOnClickListener {
            navigateToSignIn()
        }
    }


    private fun navigateToPokedex() {
        val action = FragmentRegisterDirections.actionFragmentRegisterToPokedexMainHolder()
        findNavController().navigate(action)
    }

    private fun navigateToSignIn() {
        val action = FragmentRegisterDirections.actionFragmentRegisterToFragmentSignIn()
        findNavController().navigate(action)
    }

}