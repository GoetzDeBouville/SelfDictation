package com.hellcorp.selfdictation.ui.usersetlist

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentUsersSetBinding
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.utils.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersSetFragment : BaseFragment<FragmentUsersSetBinding, UsersSetViewmodel>(
    FragmentUsersSetBinding::inflate
) {
    override val viewModel: UsersSetViewmodel by viewModel()

    override fun initViews() {
        viewModel.loadDataFromDB()
        initSpinner()
    }

    override fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    private fun renderState(state: SetListState) {
        when (state) {
            is SetListState.Loading -> setLoadingView()
            is SetListState.Empty -> setEmptyView()
            is SetListState.Content -> setContent()
        }
    }

    private fun setLoadingView() = with(binding) {
        lottieProgressBar.isVisible = true
        lottieEmptyList.isVisible = false
        spinnerFilter.isVisible = false
    }

    private fun setEmptyView() = with(binding) {
        lottieProgressBar.isVisible = false
        lottieEmptyList.isVisible = true
        spinnerFilter.isVisible = false
    }

    private fun setContent() = with(binding) {
        lottieProgressBar.isVisible = false
        lottieEmptyList.isVisible = false
        spinnerFilter.isVisible = true
    }

    private fun initSpinner() {
        val filterOptions = resources.getStringArray(R.array.filter_options)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, filterOptions)
        adapter.setDropDownViewResource(R.layout.item_dropdown_spinner)
        binding.spinnerFilter.adapter = adapter

        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.filterSetList(position + 1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }
}