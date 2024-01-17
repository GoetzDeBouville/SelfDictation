package com.hellcorp.selfdictation.ui.usersetlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hellcorp.selfdictation.databinding.ItemSetLinesBinding
import com.hellcorp.selfdictation.ui.newcard.PairTextSet

class SetTextListAdapter(private var onClicked: ((PairTextSet) -> Unit)? = null) :
    RecyclerView.Adapter<SetTextListAdapter.SetTextListViewHolder>() {
    inner class SetTextListViewHolder(
        private val binding: ItemSetLinesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pairTextSet: PairTextSet) = with(binding) {
            val textSet = pairTextSet.first
            val listOfLines = pairTextSet.second
            tvSetTitle1.text = textSet.name
            tvLine2.text = listOfLines.getOrNull(1)?.line ?: ""
            tvLine3.text = listOfLines.getOrNull(2)?.line ?: ""
            tvLine4.text = listOfLines.getOrNull(3)?.line ?: ""
            tvLine5.text = listOfLines.getOrNull(4)?.line ?: ""
            tvLine6.text = listOfLines.getOrNull(5)?.line ?: ""
        }
    }

    private var pairTextSetList: List<PairTextSet> = emptyList()

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
    }

    fun updateData(newData: List<PairTextSet>) {
        pairTextSetList = newData
        notifyDataSetChanged()
    }
}