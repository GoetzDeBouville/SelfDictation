package com.hellcorp.selfdictation.ui.main.fragments

import com.google.android.material.tabs.TabLayoutMediator
import com.hellcorp.selfdictation.databinding.FragmentMainBinding
import com.hellcorp.selfdictation.ui.main.adapters.ViewPagerAdapter
import com.hellcorp.selfdictation.ui.main.viewmodels.MainViewModel
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.applyBlurEffect
import com.hellcorp.selfdictation.utils.clearBlurEffect
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