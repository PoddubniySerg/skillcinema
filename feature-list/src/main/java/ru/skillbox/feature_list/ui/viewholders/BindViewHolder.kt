package ru.skillbox.feature_list.ui.viewholders

interface BindViewHolder {

    fun bind(any: Any, onItemClick: (Any) -> Unit)
}