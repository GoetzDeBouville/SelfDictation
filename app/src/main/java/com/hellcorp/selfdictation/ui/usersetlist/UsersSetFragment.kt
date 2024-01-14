package com.hellcorp.selfdictation.ui.usersetlist

import com.hellcorp.selfdictation.databinding.FragmentUsersSetBinding
import com.hellcorp.selfdictation.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersSetFragment : BaseFragment<FragmentUsersSetBinding, UsersSetViewmodel>(
    FragmentUsersSetBinding::inflate
) {
    override val viewModel: UsersSetViewmodel by viewModel()

    override fun initViews() {
    }

    override fun subscribe() {
    }
}