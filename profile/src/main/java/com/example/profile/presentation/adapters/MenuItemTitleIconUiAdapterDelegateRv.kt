package com.example.profile.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.R
import com.example.profile.common.extensions.inflate
import com.example.profile.databinding.ItemProfileMenuBinding
import com.example.profile.presentation.intefaces.DisplayableItem
import com.example.profile.presentation.intefaces.ItemClick
import com.example.profile.presentation.models.MenuItemTitleIconUi
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MenuItemTitleIconUiAdapterDelegateRv(
    private val itemClick: ItemClick
) :
    AbsListItemAdapterDelegate<MenuItemTitleIconUi, DisplayableItem, MenuItemTitleIconUiAdapterDelegateRv.MenuItemTitleIconHolder>() {

    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is MenuItemTitleIconUi

    override fun onCreateViewHolder(parent: ViewGroup) =
        MenuItemTitleIconHolder(parent.inflate(R.layout.item_profile_menu), itemClick)

    override fun onBindViewHolder(
        item: MenuItemTitleIconUi, holder: MenuItemTitleIconHolder, payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MenuItemTitleIconHolder(
        view: View,
        private val itemClick: ItemClick
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemProfileMenuBinding.bind(view)

        fun bind(item: MenuItemTitleIconUi) {
            binding.textViewTitle.text = item.title
            binding.imageViewIcon.setImageResource(item.idIcon)
            binding.container.setOnClickListener { itemClick.click(item.idItem) }
        }
    }
}