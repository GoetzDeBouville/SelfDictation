package com.hellcorp.selfdictation.ui.usersetlist

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentUsersSetBinding
import com.hellcorp.selfdictation.domain.models.PairTextSet
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.ui.usersetlist.adapter.SetTextListAdapter
import com.hellcorp.selfdictation.utils.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersSetFragment : BaseFragment<FragmentUsersSetBinding, UsersSetViewmodel>(
    FragmentUsersSetBinding::inflate
) {
    override val viewModel: UsersSetViewmodel by viewModel()
    private var setListData : List<PairTextSet> = emptyList()

    override fun initViews() {
        viewModel.loadDataFromDB()
        initSpinner()
        initAdapter()
    }

    override fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                renderState(it)
            }
        }

        binding.lottieAddNewSet.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newCardFragment)
        }
    }

    private fun renderState(state: SetListState) {
        Log.i("MyLog", "state = $state")
        when (state) {
            is SetListState.Loading -> setLoadingView()
            is SetListState.Empty -> setEmptyView()
            is SetListState.Content -> setContent(state)
        }
    }

    private fun setLoadingView() = with(binding) {
        lottieProgressBar.isVisible = true
        lottieEmptyList.isVisible = false
        spinnerFilter.isVisible = false
        tvTotalCount.isVisible = false
    }

    private fun setEmptyView() = with(binding) {
        lottieProgressBar.isVisible = false
        lottieEmptyList.isVisible = true
        spinnerFilter.isVisible = false
        tvTotalCount.isVisible = false
    }

    private fun setContent(content: SetListState.Content) = with(binding) {
        setListData = content.data
        lottieProgressBar.isVisible = false
        lottieEmptyList.isVisible = false
        spinnerFilter.isVisible = true
        tvTotalCount.isVisible = true
        updateAdapterData()
        setCounter(content.setsNumber)
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

    private fun initAdapter() = with(binding){
        rvSetlist.layoutManager = GridLayoutManager(requireContext(), 4)
        rvSetlist.adapter = SetTextListAdapter()
    }

    private fun updateAdapterData() {
        (binding.rvSetlist.adapter as? SetTextListAdapter)?.updateData(setListData)
    }

    private fun setCounter(setsNumber: Int) {
        binding.tvTotalCount.text = getString(R.string.found_number_sets, setsNumber.toString())
    }
}