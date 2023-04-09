package ru.skillbox.core.domain.models

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.domain.entities.ManagerFactory

class MoviesManagerFactory(private val context: Context) : ManagerFactory {

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}