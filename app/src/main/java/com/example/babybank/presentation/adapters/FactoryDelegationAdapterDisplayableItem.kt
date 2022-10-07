package com.example.babybank.presentation.adapters

import com.example.babybank.presentation.common.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class FactoryDelegationAdapterDisplayableItem @Inject constructor() {

    fun createAdapter(delegatesManager: AdapterDelegatesManager<List<DisplayableItem>>)
            : AsyncListDifferDelegationAdapter<DisplayableItem> {
        return AsyncListDifferDelegationAdapter(
            RecyclerViewAdapter.DiffUtilItemCallback(),
            delegatesManager
        )
    }
}