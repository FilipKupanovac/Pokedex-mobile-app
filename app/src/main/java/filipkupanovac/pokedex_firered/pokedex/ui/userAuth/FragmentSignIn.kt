package filipkupanovac.pokedex_firered.pokedex.ui.userAuth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentSignInBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSignIn : Fragment() {

    private val signInViewModel: SignInViewModel by viewModel()
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (signInViewModel.isUserSignedIn())
            navigateToPokedex()

        binding = FragmentSignInBinding.inflate(
            inflater, container, false
        )
        setClickListeners()
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        signInViewModel.isUserSignedIn.observe(viewLifecycleOwner) {
            if (it) {
                navigateToPokedex()
            }
        }
    }

    private fun setClickListeners() {
        binding.buttonSignIn.setOnClickListener {
            signInViewModel.signIn(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
        binding.textViewNavigateToRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToPokedex() {
        val action = FragmentSignInDirections.actionFragmentSignInToPokedexMainHolder()
        findNavController().navigate(action)
    }

    private fun navigateToRegister() {
        val action = FragmentSignInDirections.actionFragmentSignInToFragmentRegister()
        findNavController().navigate(action)
    }

}