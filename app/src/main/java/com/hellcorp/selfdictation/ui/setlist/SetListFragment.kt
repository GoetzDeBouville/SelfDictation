package com.hellcorp.selfdictation.ui.setlist

import com.hellcorp.selfdictation.databinding.FragmentSetListBinding
import com.hellcorp.selfdictation.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetListFragment : BaseFragment<FragmentSetListBinding, SetListViewModel>(FragmentSetListBinding::inflate) {

    override val viewModel: SetListViewModel by viewModel()

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }
}