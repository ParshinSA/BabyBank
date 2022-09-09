package com.example.babybank.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babybank.R
import com.example.babybank.common.extentions.inflate
import com.example.babybank.databinding.ItemItemMenuTitleIconBinding
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.common.ItemClick
import com.example.babybank.presentation.models.MenuItemTitleIconUi
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MenuItemTitleIconUiAdapterDelegateRv(
    private val itemClick: ItemClick
) :
    AbsListItemAdapterDelegate<MenuItemTitleIconUi, DisplayableItem, MenuItemTitleIconUiAdapterDelegateRv.MenuItemTitleIconHolder>() {

    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is MenuItemTitleIconUi

    override fun onCreateViewHolder(parent: ViewGroup) =
        MenuItemTitleIconHolder(parent.inflate(R.layout.item_item_menu_title_icon), itemClick)

    override fun onBindViewHolder(
        item: MenuItemTitleIconUi, holder: MenuItemTitleIconHolder, payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MenuItemTitleIconHolder(
        view: View,
        private val itemClick: ItemClick
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemItemMenuTitleIconBinding.bind(view)

        fun bind(item: MenuItemTitleIconUi) {
            binding.textViewTitle.text = item.title
            binding.imageViewIcon.setImageResource(item.idIcon)
            binding.container.setOnClickListener { itemClick.click(item.idItem) }
        }
    }
}