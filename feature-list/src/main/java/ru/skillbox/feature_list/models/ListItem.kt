package ru.skillbox.feature_list.models

interface ListItem {

    fun areItemsTheSame(newItem: ListItem): Boolean

    fun areContentsTheSame(newItem: ListItem): Boolean
}