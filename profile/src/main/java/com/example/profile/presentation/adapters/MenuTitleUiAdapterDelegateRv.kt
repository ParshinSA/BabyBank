package com.example.profile.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.R
import com.example.profile.common.extensions.inflate
import com.example.profile.databinding.ItemTitleRecyclerViewBinding
import com.example.profile.presentation.intefaces.DisplayableItem
import com.example.profile.presentation.models.MenuTitleUi
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