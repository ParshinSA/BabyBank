package com.example.babybank.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babybank.R
import com.example.babybank.common.extentions.inflate
import com.example.babybank.databinding.ItemTransferRecyclerViewBinding
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.common.ItemClick
import com.example.babybank.presentation.models.MenuItemTitleIconUi
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class TransferUiAdapterDelegateRv(
    private val itemClick: ItemClick
) : AbsListItemAdapterDelegate<MenuItemTitleIconUi, DisplayableItem, TransferUiAdapterDelegateRv.TransferItemHolder>() {

    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is MenuItemTitleIconUi

    override fun onCreateViewHolder(parent: ViewGroup) =
        TransferItemHolder(parent.inflate(R.layout.item_transfer_recycler_view), itemClick)

    override fun onBindViewHolder(
        item: MenuItemTitleIconUi, holder: TransferItemHolder, payloads: MutableList<Any>
    ) {
        holder.bind(item = item)
    }

    class TransferItemHolder(
        view: View,
        private val itemClick: ItemClick
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemTransferRecyclerViewBinding.bind(view)

        fun bind(item: MenuItemTitleIconUi) {
            binding.imageViewIconItem.setImageResource(item.idIcon)
            binding.textViewTitleItem.text = item.title

            binding.container.setOnClickListener { itemClick.click(item.idItem) }
        }
    }
}