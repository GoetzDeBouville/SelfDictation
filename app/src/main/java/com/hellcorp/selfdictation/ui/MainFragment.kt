package com.hellcorp.selfdictation.ui

import com.hellcorp.selfdictation.databinding.FragmentMainBinding
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()

    override fun initViews() {
    }

    override fun subscribe() {
        viewModel.state.observe(viewLifecycleOwner) {
            if (it is SetListState.Content) {
            } else {
            }
        }
    }
}