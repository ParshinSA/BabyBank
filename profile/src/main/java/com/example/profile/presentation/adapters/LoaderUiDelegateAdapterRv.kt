package com.example.profile.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.R
import com.example.profile.common.extensions.inflate
import com.example.profile.presentation.intefaces.DisplayableItem
import com.example.profile.presentation.models.LoaderUiRv
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class LoaderUiDelegateAdapterRv :
    AbsListItemAdapterDelegate<LoaderUiRv, DisplayableItem, LoaderUiDelegateAdapterRv.LoaderHolder>() {


    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is LoaderUiRv

    override fun onCreateViewHolder(parent: ViewGroup) = LoaderHolder(
        parent.inflate(R.layout.item_loader)
    )

    override fun onBindViewHolder(
        item: LoaderUiRv, holder: LoaderHolder, payloads: MutableList<Any>
    ) {
    }

    class LoaderHolder(view: View) : RecyclerView.ViewHolder(view)
}