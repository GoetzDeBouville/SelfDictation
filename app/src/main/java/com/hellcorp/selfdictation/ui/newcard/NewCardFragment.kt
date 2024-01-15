package com.hellcorp.selfdictation.ui.newcard

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentNewCardBinding
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.vibroError
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewCardFragment : BaseFragment<FragmentNewCardBinding, NewCardViewModel>(
    FragmentNewCardBinding::inflate
) {
    override val viewModel: NewCardViewModel by viewModel()
    private lateinit var fields: List<TextInputEditText>

    private var classNumber = 0
    override fun initViews() = with(binding) {
        fields = listOf(
            etSetTitle, etLine1, etLine2, etLine3, etLine4, etLine5, etLine6,
            etDuration1, etDuration2, etDuration3, etDuration4, etDuration5, etDuration6
        )

        initSpinner()
    }

    override fun subscribe() {
        viewModel.stateFields.observe(viewLifecycleOwner) { stateFields ->
            colorSaveButton(stateFields)
            saveButtonClickListener(stateFields)
        }
        binding.cvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        inputListener()
    }

    private fun colorSaveButton(stateFields: Boolean) = with(binding) {
        cvSave.strokeColor = if (stateFields) {
            tvSave.setTextColor(requireContext().getColor(R.color.green))
            requireContext().getColor(R.color.green)
        } else {
            tvSave.setTextColor(requireContext().getColor(R.color.gray))
            requireContext().getColor(R.color.gray)
        }
    }

    private fun initSpinner() = with(binding) {
        val filterOptions = resources.getStringArray(R.array.classes)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, filterOptions)
        adapter.setDropDownViewResource(R.layout.item_dropdown_spinner)
        spinnerFilter.adapter = adapter

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                classNumber = position + 1
                if (classNumber > 1) {
                    spinnerFilter.setBackgroundColor(requireContext().getColor(R.color.transparent))
                }
                processFields()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun inputListener() = with(binding) {
        fields.forEach {
            it.doOnTextChanged { text, _, _, _ ->
                when (it) {
                    etLine1 -> viewModel.countSymbols(text, etSymbols1)
                    etLine2 -> viewModel.countSymbols(text, etSymbols2)
                    etLine3 -> viewModel.countSymbols(text, etSymbols3)
                    etLine4 -> viewModel.countSymbols(text, etSymbols4)
                    etLine5 -> viewModel.countSymbols(text, etSymbols5)
                    etLine6 -> viewModel.countSymbols(text, etSymbols6)
                }
                processFields()
            }
        }
    }

    private fun processFields() {
        viewModel.updateStateFields(fields.all { it.text?.isNotEmpty() == true } && classNumber > 1)
    }

    private fun saveButtonClickListener(stateFields: Boolean) = with(binding) {
        cvSave.setOnClickListener {
            if (stateFields) {
                saveCard()
            } else {
                showValidation()
            }
        }
    }

    private fun saveCard() {

    }

    private fun showValidation() {
        requireContext().vibroError()
        if (classNumber == 1) {
            binding.spinnerFilter.setBackgroundResource(R.drawable.error_background)
        }
        fields.forEach {
            if (it.text.isNullOrEmpty()) {
                it.error = "Поле должно быть заполнено"
                it.setBackgroundDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.error_background
                    )
                )
            }
        }
    }
}