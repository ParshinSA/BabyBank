package com.example.babybank.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babybank.R
import com.example.babybank.common.extentions.inflate
import com.example.babybank.databinding.ItemTitleRecyclerViewBinding
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.MenuTitleUi
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MenuTitleUiAdapterDelegateRv :
    AbsListItemAdapterDelegate<MenuTitleUi, DisplayableItem,
            MenuTitleUiAdapterDelegateRv.MenuTitleUiHolder>() {

    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is MenuTitleUi

    override fun onCreateViewHolder(parent: ViewGroup) = MenuTitleUiHolder(
        parent.inflate(R.layout.item_title_recycler_view)
    )

    override fun onBindViewHolder(
        item: MenuTitleUi, holder: MenuTitleUiHolder, payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MenuTitleUiHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemTitleRecyclerViewBinding.bind(view)

        fun bind(item: MenuTitleUi) {
            binding.textViewMenuTitle.text = view.context.resources.getString(item.title)
        }
    }

}