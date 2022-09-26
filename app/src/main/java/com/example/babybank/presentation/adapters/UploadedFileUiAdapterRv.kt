package com.example.babybank.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.babybank.R
import com.example.babybank.common.extentions.inflate
import com.example.babybank.databinding.ItemFileRvBinding
import com.example.babybank.presentation.common.DisplayableItem
import com.example.babybank.presentation.models.UploadedFileUi
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class UploadedFileUiAdapterRv(
    private val openFile: (name: String) -> Unit
) : AbsListItemAdapterDelegate<UploadedFileUi, DisplayableItem, UploadedFileUiAdapterRv.UploadedFileUiHolder>() {

    override fun isForViewType(
        item: DisplayableItem, items: MutableList<DisplayableItem>, position: Int
    ) = item is UploadedFileUi

    override fun onCreateViewHolder(parent: ViewGroup) =
        UploadedFileUiHolder(view = parent.inflate(R.layout.item_file_rv), openFile)

    override fun onBindViewHolder(
        item: UploadedFileUi, holder: UploadedFileUiHolder, payloads: MutableList<Any>
    ) {
        holder.bind(item = item)
    }

    class UploadedFileUiHolder(
        view: View,
        private val openFile: (name: String) -> Unit,
    ) : RecyclerView.ViewHolder(view) {

        val binding = ItemFileRvBinding.bind(view)

        fun bind(item: UploadedFileUi) {
            binding.textViewNameFile.text = item.name
            binding.appCompatButtonOpenFile.setOnClickListener { openFile(item.name) }
        }
    }
}
