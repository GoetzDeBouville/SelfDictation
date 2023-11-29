package com.hellcorp.selfdictation.ui.main

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentMainBinding
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.Tools
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()
    private var index = 1

    override fun initViews() = with(binding) {
        val bundle = arguments?.getStringArray(Tools.LIST_LINES)

        val setTitle = bundle?.get(0)
        val line1 = bundle?.get(1)

        val arr: Array<Pair<String?, Int?>> = arrayOf(
            (bundle?.get(1) to bundle?.get(7)?.toInt()),
            (bundle?.get(2) to bundle?.get(8)?.toInt()),
            (bundle?.get(3) to bundle?.get(9)?.toInt()),
            (bundle?.get(4) to bundle?.get(10)?.toInt()),
            (bundle?.get(5) to bundle?.get(11)?.toInt()),
            (bundle?.get(6) to bundle?.get(12)?.toInt())
        )
        tvSetNum.text = setTitle
        tvLine1.text = line1

        tvAmountExecutionTime.text =
            getString(R.string.amount_execution_time, viewModel.extractNumber(setTitle.toString()))

        cvNextLine.setOnClickListener {
            Tools.vibroManager(requireContext(), 50)
            viewLifecycleOwner.lifecycleScope.launch {
                tvLine1.visibility = View.VISIBLE
                arr[index - 1].second?.toLong()?.times(1000L)?.let { it1 -> delay(it1) }
                tvLine1.visibility = View.GONE
            }
            ivNextLine.setImageResource(R.drawable.ic_next)

            fetchLines(arr, index, bundle?.get(index).toString())
            if (index == 1) {
                viewModel.startTimer()
                tvNextLine.text = getString(R.string.show_line)
            }
            index++
            if (index > 6) {
                cvNextLine.isClickable = false
                cvNextLine.visibility = View.INVISIBLE
            }
        }
    }

    override fun subscribe() = with(binding) {
        llBackToSetlist.setOnClickListener {
            findNavController().popBackStack()
            Tools.vibroManager(requireContext(), 50)
        }

        viewModel.time.observe(viewLifecycleOwner) { timeString ->
            tvExecutionTime.text = timeString
        }

        cvStopExecution.setOnClickListener {
            viewModel.stopTimer()
            Tools.vibroManager(requireContext(), 50)
            cvNextLine.isClickable = false
            cvNextLine.visibility = View.INVISIBLE
            Tools.showSnackbar(
                root,
                "Выполнение задания остановлено. Время выполнения: ${tvExecutionTime.text}",
                requireContext()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopTimer()
    }

    private fun fetchLines(arr: Array<Pair<String?, Int?>>, index: Int, line: String) =
        with(binding) {
            tvLineNum.text = getString(R.string.line_number, index.toString(), arr.size.toString())

            tvLetterSecNum.text = getString(
                R.string.letters_sec_num,
                viewModel.countLetters(arr[index - 1].first.toString()).toString(),
                arr[index - 1].second.toString()
            )
            tvLine1.text = line
            tvLine1.visibility = View.VISIBLE
            tvAmountExecutionTime.visibility = View.VISIBLE
        }
}