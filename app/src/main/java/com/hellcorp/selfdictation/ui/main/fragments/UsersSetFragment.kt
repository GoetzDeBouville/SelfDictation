package com.hellcorp.selfdictation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentUsersSetBinding
import com.hellcorp.selfdictation.ui.main.viewmodels.UsersSetViewmodel
import com.hellcorp.selfdictation.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersSetFragment : BaseFragment<FragmentUsersSetBinding, UsersSetViewmodel>(
    FragmentUsersSetBinding::inflate
) {
    override val viewModel: UsersSetViewmodel by viewModel()

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }
}