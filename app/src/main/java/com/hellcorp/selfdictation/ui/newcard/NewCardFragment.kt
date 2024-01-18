package com.hellcorp.selfdictation.ui.newcard

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentNewCardBinding
import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet
import com.hellcorp.selfdictation.ui.usersetlist.adapter.CustomArrayAdapter
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.Tools
import com.hellcorp.selfdictation.utils.vibroError
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.Duration.Companion.microseconds

open class NewCardFragment : BaseFragment<FragmentNewCardBinding, NewCardViewModel>(
    FragmentNewCardBinding::inflate
) {
    override val viewModel: NewCardViewModel by viewModel()
    private lateinit var fields: List<TextInputEditText>
    private lateinit var mapOfLinesToDuration: Map<TextInputEditText, TextInputEditText>
    private var setId: Int? = null
    private var pairTextSet : PairTextSet? = null
    private var classNumber = 0

    override fun initViews() = with(binding) {
        fields = listOf(
            etSetTitle, etLine1, etLine2, etLine3, etLine4, etLine5, etLine6,
            etDuration1, etDuration2, etDuration3, etDuration4, etDuration5, etDuration6
        )
        mapOfLinesToDuration = mapOf(
                etDuration1 to etLine1, etDuration2 to etLine2, etDuration3 to etLine3,
                etDuration4 to etLine4, etDuration5 to etLine5, etDuration6 to etLine6
            )

        getSetId()
        initSpinner()
    }

    override fun subscribe() {
        getCardSetData(setId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateData.collect { state ->
                if (state is CardState.Content) {
                    pairTextSet = state.data
                    fetchData(state.data)
                }
            }
        }

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
        val adapter = CustomArrayAdapter(requireContext(), R.layout.item_spinner, filterOptions)
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
                    etLine1 -> viewModel.countSymbolsAndSetToEditText(text, etSymbols1)
                    etLine2 -> viewModel.countSymbolsAndSetToEditText(text, etSymbols2)
                    etLine3 -> viewModel.countSymbolsAndSetToEditText(text, etSymbols3)
                    etLine4 -> viewModel.countSymbolsAndSetToEditText(text, etSymbols4)
                    etLine5 -> viewModel.countSymbolsAndSetToEditText(text, etSymbols5)
                    etLine6 -> viewModel.countSymbolsAndSetToEditText(text, etSymbols6)
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

    private fun saveCard() = with(binding) {
        val setLines = buildSet()
        val finalListOfLines = buildListOfLines()
        val preparedData = PairTextSet.create(setLines, finalListOfLines)
        Log.i("MyLog", "NewCarsFragment prepareData = $preparedData")
        viewModel.saveDataToDB(preparedData)
        findNavController().popBackStack()
    }

    private fun buildSet() = TextSet(
        id = setId ?: 0,
        name = binding.etSetTitle.text.toString(),
        classNumber = classNumber
    )

    private fun buildListOfLines(): List<Line> {
        val listOfLines: MutableList<Line> = mutableListOf()
        var counter = 0
        mapOfLinesToDuration.forEach {
            counter += 1
            val line = Line(
                id = pairTextSet?.second?.get(counter-1)?.id ?: 0,
                number = counter,
                line = it.value.text.toString(),
                letersNum = viewModel.countLetters(it.value.text ?: ""),
                timeSec = it.key.text.toString().toInt()
            )
            listOfLines.add(line)
        }
        return listOfLines.toList()
    }

    private fun showValidation() {
        requireContext().vibroError()
        if (classNumber == 1) {
            binding.spinnerFilter.setBackgroundResource(R.drawable.error_background)
        }
        fields.forEach {
            if (it.text.isNullOrEmpty()) {
                it.error = getString(R.string.error_message)
                it.setBackgroundDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.error_background
                    )
                )
            }
        }
    }

    private fun getSetId() {
        setId = arguments?.getInt(Tools.SET_ID)
    }

    private fun getCardSetData(setId: Int?) {
        if (setId != null) {
            viewModel.getData(setId)
        }
    }

    private fun fetchData(pairTextSet: PairTextSet) = with(binding) {
        spinnerFilter.setSelection(pairTextSet.first.classNumber - 1)
        etSetTitle.setText(pairTextSet.first.name)
        etLine1.setText(pairTextSet.second[0].line)
        etDuration1.setText(pairTextSet.second[0].timeSec.toString())
        etLine2.setText(pairTextSet.second[1].line)
        etDuration2.setText(pairTextSet.second[1].timeSec.toString())
        etLine3.setText(pairTextSet.second[2].line)
        etDuration3.setText(pairTextSet.second[2].timeSec.toString())
        etLine4.setText(pairTextSet.second[3].line)
        etDuration4.setText(pairTextSet.second[3].timeSec.toString())
        etLine5.setText(pairTextSet.second[4].line)
        etDuration5.setText(pairTextSet.second[4].timeSec.toString())
        etLine6.setText(pairTextSet.second[5].line)
        etDuration6.setText(pairTextSet.second[5].timeSec.toString())
    }
}