package ru.skillbox.core.domain.entities

import androidx.recyclerview.widget.RecyclerView

interface ManagerFactory {

    fun getLayoutManager(): RecyclerView.LayoutManager
}