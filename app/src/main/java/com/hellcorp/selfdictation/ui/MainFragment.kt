package com.hellcorp.selfdictation.ui


import androidx.fragment.app.viewModels
import com.hellcorp.selfdictation.databinding.FragmentMainBinding
import com.hellcorp.selfdictation.utils.BaseFragment

internal class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel: MainViewModel by viewModels()

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }
}