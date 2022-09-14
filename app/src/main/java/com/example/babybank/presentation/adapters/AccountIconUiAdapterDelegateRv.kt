package com.example.babybank.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babybank.R
import com.example.babybank.common.extentions.inflate
import com.example.babybank.databinding.ItemAccountIconRecyclerViewBinding
import com.example.babybank.presentation.adapters.AccountIconUiAdapterDelegateRv.AccountIconUiHolder
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.common.ItemClick
import com.example.babybank.presentation.models.AccountIconUi
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class AccountIconUiAdapterDelegateRv(
    private val itemClick: ItemClick
) : AbsListItemAdapterDelegate<AccountIconUi, DisplayableItem, AccountIconUiHolder>() {

    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is AccountIconUi

    override fun onCreateViewHolder(parent: ViewGroup) =
        AccountIconUiHolder(parent.inflate(R.layout.item_account_icon_recycler_view), itemClick)

    override fun onBindViewHolder(
        item: AccountIconUi, holder: AccountIconUiHolder, payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class AccountIconUiHolder(
        view: View,
        private val itemClick: ItemClick,
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemAccountIconRecyclerViewBinding.bind(view)

        fun bind(item: AccountIconUi) {
            binding.textViewBalance.text = item.balance
            binding.imageViewIconCurrency.setImageResource(item.currencyTypeIcon)
            binding.container.setOnClickListener { itemClick.click(item.idItem) }
        }
    }

}