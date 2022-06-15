package filipkupanovac.pokedex_firered.pokedex.ui.pokedexMainHolder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerTabStrip
import androidx.viewpager.widget.ViewPager
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentMainHolderBinding

class FragmentMainHolder : Fragment() {

    private lateinit var binding: FragmentMainHolderBinding
    lateinit var vPager : ViewPager
    private lateinit var activityContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainHolderBinding.inflate(inflater, container, false)

        activityContext = activity?.applicationContext!!

        SetupViewPager()

        return binding.root
    }

    private fun SetupViewPager() {
        val mainHolderFragmentAdapter =
            activity?.supportFragmentManager?.let { MainHolderFragmentAdapter(activityContext, it) }
        val tabLayout = binding.tabLayout
        vPager = binding.viewPager
        vPager.adapter = mainHolderFragmentAdapter
        tabLayout.setupWithViewPager(vPager)
        vPager.setCurrentItem(1,true)
    }
}