package com.example.babybank.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.babybank.presentation.common.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class RecyclerViewAdapter :
    AsyncListDifferDelegationAdapter<DisplayableItem>(DiffUtilItemCallback()) {

    class DiffUtilItemCallback : DiffUtil.ItemCallback<DisplayableItem>() {

        override fun areItemsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
            return oldItem.idItem == newItem.idItem
        }

        override fun areContentsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem) =
            oldItem.hashCode() == newItem.hashCode()
    }
}