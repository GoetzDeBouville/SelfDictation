package com.hellcorp.selfdictation.ui.main

import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentMainBinding
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.Tools
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()
    private var index = 1

    override fun initViews() = with(binding) {
        val bundle = arguments?.getStringArray(Tools.LIST_LINES)

        val setTitle = bundle?.get(0)
        val line1 = bundle?.get(1)
        val line2 = bundle?.get(2)
        val line3 = bundle?.get(3)
        val line4 = bundle?.get(4)
        val line5 = bundle?.get(5)
        val line6 = bundle?.get(6)

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
        tvLine2.text = line2
        tvLine3.text = line3
        tvLine4.text = line4
        tvLine5.text = line5
        tvLine6.text = line6


        tvAmountExecutionTime.text =
            getString(R.string.amount_execution_time, setTitle?.get(setTitle.length - 1))

        cvNextLine.setOnClickListener {
            cvNextLine.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
            fetchLines(arr, index, bundle?.get(index).toString())
            index++
            if (index > 6) {
                cvNextLine.isClickable = false
            }
        }

//
//        val bottomSheetContainer = binding.llBottomSheet
//        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout> =
//            BottomSheetBehavior.from(bottomSheetContainer).apply {
//                state = BottomSheetBehavior.STATE_HIDDEN
//            }
//        bottomSheetObserver(bottomSheetBehavior, binding.overlay)
//
//        binding.llShowSetList.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        }
    }

    override fun subscribe() = with(binding) {
        llBackToSetlist.setOnClickListener {
            findNavController().popBackStack()
        }
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
        }

    private fun bottomSheetObserver(
        bottomSheetBehavior: BottomSheetBehavior<LinearLayout>,
        overlay: View
    ) {
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) overlay.visibility = View.GONE
                else overlay.visibility = View.VISIBLE
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                overlay.alpha = slideOffset
            }
        })
    }
}