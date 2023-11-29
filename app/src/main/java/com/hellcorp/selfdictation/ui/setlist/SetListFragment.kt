package com.hellcorp.selfdictation.ui.setlist

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.databinding.FragmentSetListBinding
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.Tools
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetListFragment :
    BaseFragment<FragmentSetListBinding, SetListViewModel>(FragmentSetListBinding::inflate) {

    override val viewModel: SetListViewModel by viewModel()

    override fun initViews() {
    }

    override fun subscribe() = with(binding) {
        set1.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle1.text.toString(),
                line1.text.toString(),
                line2.text.toString(),
                line3.text.toString(),
                line4.text.toString(),
                line5.text.toString(),
                line6.text.toString(),
                4,
                4,
                5,
                5,
                5,
                6
            )
            findNavController().navigate(
                R.id.action_setListFragment_to_mainFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }
    }

    private fun fillTheBundle(
        setTitle: String,
        line1: String,
        line2: String,
        line3: String,
        line4: String,
        line5: String,
        line6: String,
        seconds1: Int,
        seconds2: Int,
        seconds3: Int,
        seconds4: Int,
        seconds5: Int,
        seconds6: Int
    ): Array<String> {
        return arrayOf(
            setTitle,
            line1,
            line2,
            line3,
            line4,
            line5,
            line6,
            seconds1.toString(),
            seconds2.toString(),
            seconds3.toString(),
            seconds4.toString(),
            seconds5.toString(),
            seconds6.toString()
        )
    }
}