package ru.skillbox.core.ui.viewholders

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding

abstract class CollectionViewHolder<T>(
    protected val binding: CollectionBinding,
    private val dpListTopMargin: Int,
    private val dpRootTopMargin: Int
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            val dpRatio = collection.resources.displayMetrics.density
            val pxListTopMargin = (dpListTopMargin * dpRatio).toInt()
            (collection.layoutParams as ConstraintLayout.LayoutParams).updateMargins(top = pxListTopMargin)
            val pxRootTopMargin = (dpRootTopMargin * dpRatio).toInt()
            (root.layoutParams as RecyclerView.LayoutParams).updateMargins(top = pxRootTopMargin)
        }
    }

    abstract fun bind(item: T)
}