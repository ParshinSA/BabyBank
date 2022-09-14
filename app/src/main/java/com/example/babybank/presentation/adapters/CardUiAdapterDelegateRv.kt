package com.example.babybank.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babybank.R
import com.example.babybank.common.extentions.inflate
import com.example.babybank.databinding.ItemCardRecyclerViewBinding
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.common.ItemClick
import com.example.babybank.presentation.models.CardUi
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class CardUiAdapterDelegateRv(
    private val itemClick: ItemClick
) : AbsListItemAdapterDelegate<CardUi, DisplayableItem, CardUiAdapterDelegateRv.CardHolder>() {

    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is CardUi

    override fun onCreateViewHolder(parent: ViewGroup) = CardHolder(
        parent.inflate(R.layout.item_card_recycler_view), itemClick
    )

    override fun onBindViewHolder(item: CardUi, holder: CardHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class CardHolder(
        view: View,
        private val itemClick: ItemClick
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCardRecyclerViewBinding.bind(view)

        fun bind(item: CardUi) {
            binding.textViewShortIdCard.text = item.shortId
            binding.textViewBalance.text = item.balance
            binding.container.setOnClickListener { itemClick.click(item.idItem) }
        }
    }

}