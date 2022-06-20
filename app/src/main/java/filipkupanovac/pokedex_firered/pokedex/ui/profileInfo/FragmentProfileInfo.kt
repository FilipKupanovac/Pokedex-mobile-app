package filipkupanovac.pokedex_firered.pokedex.ui.profileInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentProfileInfoBinding


class FragmentProfileInfo : Fragment() {

    private lateinit var binding: FragmentProfileInfoBinding
    private val args: FragmentProfileInfoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileInfoBinding.inflate(
            inflater, container, false
        )

        setClickListeners()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setClickListeners() {
        binding.logOutButton.setOnClickListener {
            logOut()
        }

        binding.textViewDeletion.setOnClickListener {
            deleteAccount()
        }

        binding.imageButtonBack.setOnClickListener {
            navigateToMainHolder()
        }

    }

    private fun navigateToMainHolder() {
        val action =
            FragmentProfileInfoDirections.actionFragmentProfileInfoToFragmentMainHolder(args.activeFragmentPositionOnCall)
        findNavController().navigate(action)
    }

    private fun deleteAccount() {
        logOut("DELETED ACCOUNT")
    }

    private fun logOut(message: CharSequence = "LOGGED OUT") {
        val action = FragmentProfileInfoDirections.actionFragmentProfileInfoToFragmentSignIn()
        findNavController().navigate(action)
        Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}