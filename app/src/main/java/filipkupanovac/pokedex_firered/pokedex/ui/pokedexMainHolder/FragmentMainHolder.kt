package filipkupanovac.pokedex_firered.pokedex.ui.pokedexMainHolder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentMainHolderBinding

class FragmentMainHolder : Fragment() {

    private lateinit var binding: FragmentMainHolderBinding
    private lateinit var vPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var activityContext: Context

    private val args: FragmentMainHolderArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainHolderBinding.inflate(inflater, container, false)

        activityContext = activity?.applicationContext!!

        setupViewPager()
        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners() {
        binding.usernamePlaceholderMainHolderFragment.setOnClickListener {
            val action = FragmentMainHolderDirections.actionFragmentMainHolderToFragmentProfileInfo(
                vPager.currentItem.toLong()
            )
            findNavController().navigate(action)
        }
    }

    private fun setupViewPager() {
        val mainHolderFragmentAdapter = MainHolderFragmentAdapter(activityContext,childFragmentManager)
        vPager = binding.viewPager
        vPager.adapter = mainHolderFragmentAdapter
        tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(vPager)
        vPager.setCurrentItem(args.viewPagerDisplayPage.toInt(), true)
    }

    override fun onResume() {
        super.onResume()
        setupViewPager()
    }
}