package com.hellcorp.selfdictation.ui.usersetlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hellcorp.selfdictation.databinding.ItemSetLinesBinding
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet

class SetTextListAdapter(
    private var onSetClicked: ((PairTextSet) -> Unit)? = null,
    private var onSetLongClicked: ((PairTextSet, View) -> Unit)? = { _, _ -> }
) :
    RecyclerView.Adapter<SetTextListAdapter.SetTextListViewHolder>() {
    private var pairTextSetList: List<PairTextSet> = emptyList()

    inner class SetTextListViewHolder(
        private val binding: ItemSetLinesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pairTextSet: PairTextSet) = with(binding) {
            val textSet = pairTextSet.first
            val listOfLines = pairTextSet.second
            tvSetTitle1.text = textSet.name
            tvLine1.text = listOfLines.getOrNull(0)?.line ?: ""
            tvLine2.text = listOfLines.getOrNull(1)?.line ?: ""
            tvLine3.text = listOfLines.getOrNull(2)?.line ?: ""
            tvLine4.text = listOfLines.getOrNull(3)?.line ?: ""
            tvLine5.text = listOfLines.getOrNull(4)?.line ?: ""
            tvLine6.text = listOfLines.getOrNull(5)?.line ?: ""

            itemView.setOnLongClickListener {
                this@SetTextListAdapter.onSetLongClicked?.invoke(pairTextSet, itemView)
                true
            }
        }
    }

    inner class SetTextListCallback(
        private val oldList: List<PairTextSet>,
        private val newList: List<PairTextSet>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    fun setOnClickListener(listener: (PairTextSet) -> Unit) {
        onSetClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetTextListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSetLinesBinding.inflate(layoutInflater, parent, false)
        return SetTextListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pairTextSetList.size
    }

    override fun onBindViewHolder(holder: SetTextListViewHolder, position: Int) {
        holder.bind(pairTextSetList[position])
        holder.itemView.setOnClickListener {
            onSetClicked?.invoke(pairTextSetList[position])
        }
    }

    fun updateData(newData: List<PairTextSet>) {
        val diffCallback = SetTextListCallback(pairTextSetList, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        pairTextSetList = newData
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}