package com.hellcorp.selfdictation.ui.usersetlist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hellcorp.selfdictation.R
import com.hellcorp.selfdictation.RootActivity
import com.hellcorp.selfdictation.databinding.FragmentUsersSetBinding
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.ui.main.fragments.MainFragment
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet
import com.hellcorp.selfdictation.ui.usersetlist.adapter.CustomArrayAdapter
import com.hellcorp.selfdictation.ui.usersetlist.adapter.SetTextListAdapter
import com.hellcorp.selfdictation.utils.BaseFragment
import com.hellcorp.selfdictation.utils.Tools
import com.hellcorp.selfdictation.utils.applyBlurEffect
import com.hellcorp.selfdictation.utils.clearBlurEffect
import com.hellcorp.selfdictation.utils.debounce
import com.hellcorp.selfdictation.utils.getquantityString
import com.hellcorp.selfdictation.utils.vibrateShot
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersSetFragment : BaseFragment<FragmentUsersSetBinding, UsersSetViewmodel>(
    FragmentUsersSetBinding::inflate
) {
    override val viewModel: UsersSetViewmodel by viewModel()
    private var onSetClicked: ((PairTextSet) -> Unit)? = null
    private val listAdapter = SetTextListAdapter(
        onSetClicked,
        onSetLongClicked = ::showPowerMenu
    )

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

        setCicklisteners()
    }

    private fun renderState(state: SetListState) {
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
        lottieProgressBar.isVisible = false
        lottieEmptyList.isVisible = false
        spinnerFilter.isVisible = true
        tvTotalCount.isVisible = true

        listAdapter.updateData(content.data)
        setCounter(content.setsNumber)
    }

    private fun initSpinner() {
        val filterOptions = resources.getStringArray(R.array.filter_options)
        val adapter = CustomArrayAdapter(requireContext(), R.layout.item_spinner, filterOptions)
        adapter.setDropDownViewResource(R.layout.item_dropdown_spinner)
        binding.spinnerFilter.adapter = adapter

        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    viewModel.filterSetList(position + 1)
                } else {
                    viewModel.loadDataFromDB()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun initAdapter() = with(binding) {
        rvSetlist.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = listAdapter
        }
    }

    private fun setCounter(setsNumber: Int) {
        binding.tvTotalCount.text = setsNumber.getquantityString(requireContext())
    }

    private fun setCicklisteners() {
        onSetClicked = debounce(
            Tools.CLICK_DEBOUNCE_DELAY_500MS,
            viewLifecycleOwner.lifecycleScope
        ) {
            val cardData = viewModel.buildDataForBundle(it)
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment,
                bundleOf(Tools.LIST_LINES to cardData)
            )
        }
        listAdapter.setOnClickListener(onSetClicked!!)
    }

    private fun showPowerMenu(pairTextSet: PairTextSet, anchorView: View) {
        requireContext().vibrateShot(VIBRATE_DURATION_100MS)
        val powerMenu = buildMenu()

        powerMenu.setOnMenuItemClickListener { position, _ ->
            when (position) {
                0 -> {
                    findNavController().navigate(
                        R.id.action_mainFragment_to_newCardFragment,
                        bundleOf(Tools.SET_ID to pairTextSet.first.id)
                    )
                    powerMenu.dismiss()
                }

                1 -> {
                    buildDialog(pairTextSet)
                    powerMenu.dismiss()
                }
            }
        }
        powerMenu.showAsAnchorLeftBottom(anchorView)
    }


    private fun buildMenu(): PowerMenu {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)!!

        return PowerMenu.Builder(requireContext())
            .setLifecycleOwner(viewLifecycleOwner)
            .addItem(PowerMenuItem(getString(R.string.edit), iconRes = R.drawable.ic_pencil))
            .addItem(PowerMenuItem(getString(R.string.delete), iconRes = R.drawable.ic_garbage))
            .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
            .setMenuRadius(MENU_RADIUS)
            .setShowBackground(true)
            .setMenuShadow(MENU_SHADOW)
            .setMenuColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setTextSize(TEXT_SIZE)
            .setTextTypeface(typeface)
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
            .setSize(
                viewModel.dpToPx(MENU_WIDTH_DP, requireContext()),
                viewModel.dpToPx(MENU_HEIGHT_DP, requireContext())
            )
            .setPadding(MENU_PADDING)
            .build()
    }

    private fun buildDialog(pairTextSet: PairTextSet) {
        val setTitle = pairTextSet.first.name
        applyBlur()
        val alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(requireContext(), R.style.AlertDialog)
        alertDialogBuilder.setTitle(getString(R.string.remove_dialog))
            .setMessage(getString(R.string.remove_set, setTitle))
            .setPositiveButton(getString(R.string.delete)) { _: DialogInterface, _: Int ->
                viewModel.removeSet(pairTextSet)
                clearBlurEffect()
                showSnackBar(setTitle)
            }
            .setNegativeButton(getString(R.string.no)) { _: DialogInterface, _: Int ->
                clearBlurEffect()
            }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.setOnCancelListener {
            clearBlurEffect()
        }
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(resources.getColor(R.color.black, null))
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(resources.getColor(R.color.red, null))
    }

    private fun applyBlur() {
        (requireActivity() as RootActivity).applyBlurEffect()
    }

    private fun clearBlurEffect() {
        (requireActivity() as RootActivity).clearBlurEffect()
    }

    private fun showSnackBar(setTitle: String) {
        Tools.showSnackbar(binding.root, getString(R.string.successfully_removed, setTitle), requireContext())
    }

    companion object {
        private const val MENU_WIDTH_DP = 260
        private const val MENU_HEIGHT_DP = 180
        private const val MENU_PADDING = 16
        private const val MENU_RADIUS = 48f
        private const val MENU_SHADOW = 48f
        private const val TEXT_SIZE = 18
        private const val VIBRATE_DURATION_100MS = 100L
    }
}