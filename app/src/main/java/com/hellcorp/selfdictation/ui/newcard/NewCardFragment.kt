package com.hellcorp.selfdictation.ui.newcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentNewCardBinding
import com.hellcorp.selfdictation.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewCardFragment : BaseFragment<FragmentNewCardBinding, NewCardViewModel>(
    FragmentNewCardBinding::inflate
) {
    override val viewModel: NewCardViewModel by viewModel()
    override fun initViews() {
        initSpinner()
    }

    override fun subscribe() {
        binding.cvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initSpinner() {
        val filterOptions = resources.getStringArray(R.array.classes)
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
                Unit
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }
}