package ru.skillbox.core.domain.entities

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

interface AdapterFactory<I, VH : ViewHolder> {

    fun getAdapter(onClickAllButton: () -> Unit): ListAdapter<I, VH>
}