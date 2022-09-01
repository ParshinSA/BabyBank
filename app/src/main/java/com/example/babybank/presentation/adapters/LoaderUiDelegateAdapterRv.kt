package com.example.babybank.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babybank.R
import com.example.babybank.common.extentions.inflate
import com.example.babybank.presentation.models.DisplayableItem
import com.example.babybank.presentation.models.LoaderUiRv
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