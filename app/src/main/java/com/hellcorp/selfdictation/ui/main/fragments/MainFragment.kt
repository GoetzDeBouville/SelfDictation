package com.hellcorp.selfdictation.ui.main.fragments

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentMainBinding
import com.hellcorp.selfdictation.ui.main.adapters.ViewPagerAdapter
import com.hellcorp.selfdictation.ui.main.viewmodels.MainViewModel
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.Tools
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()
    override fun initViews() {
        initViewPager()
    }

    override fun subscribe() {
    }

    private fun initViewPager() {
        val viewPager = binding.viewPager
        val tablayout = binding.tablayout

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            tab.text = TABS_TITLES[position]
        }.attach()
    }

    companion object {
        private val TABS_TITLES = arrayOf("Базовый набор", "Пользовательский набор")
    }
}