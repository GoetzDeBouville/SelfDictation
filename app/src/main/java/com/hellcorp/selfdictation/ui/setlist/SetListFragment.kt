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
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set2.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle2.text.toString(),
                line7.text.toString(),
                line8.text.toString(),
                line9.text.toString(),
                line10.text.toString(),
                line11.text.toString(),
                line12.text.toString(),
                6,
                6,
                6,
                7,
                7,
                8
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set3.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle3.text.toString(),
                line13.text.toString(),
                line14.text.toString(),
                line15.text.toString(),
                line16.text.toString(),
                line17.text.toString(),
                line18.text.toString(),
                8,
                8,
                8,
                8,
                8,
                8
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set4.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle4.text.toString(),
                line19.text.toString(),
                line20.text.toString(),
                line21.text.toString(),
                line22.text.toString(),
                line23.text.toString(),
                line24.text.toString(),
                8,
                7,
                7,
                7,
                7,
                7
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set5.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle5.text.toString(),
                line25.text.toString(),
                line26.text.toString(),
                line27.text.toString(),
                line28.text.toString(),
                line29.text.toString(),
                line30.text.toString(),
                6,
                6,
                6,
                6,
                6,
                6
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set6.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle6.text.toString(),
                line31.text.toString(),
                line32.text.toString(),
                line33.text.toString(),
                line34.text.toString(),
                line35.text.toString(),
                line36.text.toString(),
                5,
                5,
                5,
                5,
                5,
                5
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set7.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle7.text.toString(),
                line37.text.toString(),
                line38.text.toString(),
                line39.text.toString(),
                line40.text.toString(),
                line41.text.toString(),
                line42.text.toString(),
                4,
                4,
                4,
                4,
                4,
                4
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set8.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle8.text.toString(),
                line43.text.toString(),
                line44.text.toString(),
                line45.text.toString(),
                line46.text.toString(),
                line47.text.toString(),
                line48.text.toString(),
                5,
                7,
                8,
                9,
                10,
                10
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set9.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle9.text.toString(),
                line49.text.toString(),
                line50.text.toString(),
                line51.text.toString(),
                line52.text.toString(),
                line53.text.toString(),
                line54.text.toString(),
                7,
                7,
                7,
                7,
                7,
                7
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set10.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle10.text.toString(),
                line55.text.toString(),
                line56.text.toString(),
                line57.text.toString(),
                line58.text.toString(),
                line59.text.toString(),
                line60.text.toString(),
                5,
                5,
                5,
                5,
                5,
                5
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set11.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle11.text.toString(),
                line61.text.toString(),
                line62.text.toString(),
                line63.text.toString(),
                line64.text.toString(),
                line65.text.toString(),
                line66.text.toString(),
                4,
                4,
                4,
                4,
                4,
                4
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set12.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle12.text.toString(),
                line67.text.toString(),
                line68.text.toString(),
                line69.text.toString(),
                line70.text.toString(),
                line71.text.toString(),
                line72.text.toString(),
                4,
                4,
                4,
                4,
                4,
                4
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set13.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle13.text.toString(),
                line73.text.toString(),
                line74.text.toString(),
                line75.text.toString(),
                line76.text.toString(),
                line77.text.toString(),
                line78.text.toString(),
                4,
                4,
                4,
                4,
                4,
                4
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set14.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle14.text.toString(),
                line79.text.toString(),
                line80.text.toString(),
                line81.text.toString(),
                line82.text.toString(),
                line83.text.toString(),
                line84.text.toString(),
                4,
                4,
                4,
                4,
                4,
                4
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set15.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle15.text.toString(),
                line85.text.toString(),
                line86.text.toString(),
                line87.text.toString(),
                line88.text.toString(),
                line89.text.toString(),
                line90.text.toString(),
                4,
                4,
                4,
                4,
                4,
                4
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set16.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle16.text.toString(),
                line91.text.toString(),
                line92.text.toString(),
                line93.text.toString(),
                line94.text.toString(),
                line95.text.toString(),
                line96.text.toString(),
                5,
                5,
                5,
                5,
                5,
                5
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set17.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle17.text.toString(),
                line97.text.toString(),
                line98.text.toString(),
                line99.text.toString(),
                line100.text.toString(),
                line101.text.toString(),
                line102.text.toString(),
                5,
                6,
                6,
                6,
                7,
                7
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
            )
        }

        set18.setOnClickListener {
            val listLines = fillTheBundle(
                setTitle18.text.toString(),
                line103.text.toString(),
                line104.text.toString(),
                line105.text.toString(),
                line106.text.toString(),
                line107.text.toString(),
                line108.text.toString(),
                7,
                7,
                7,
                8,
                8,
                8
            )
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment, bundleOf(Tools.LIST_LINES to listLines)
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