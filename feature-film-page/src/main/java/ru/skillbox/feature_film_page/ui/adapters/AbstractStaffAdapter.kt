package ru.skillbox.feature_film_page.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.ui.viewholders.CollectionViewHolder
import ru.skillbox.feature_film_page.models.TranslatableStaffItem

abstract class AbstractStaffAdapter :
    RecyclerView.Adapter<CollectionViewHolder<List<TranslatableStaffItem>>>() {

    private var items: List<TranslatableStaffItem>? = null

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(
        holder: CollectionViewHolder<List<TranslatableStaffItem>>,
        position: Int
    ) {
        if (items == null) {
            return
        }
        holder.bind(items!!)
    }

    fun setItems(items: List<TranslatableStaffItem>) {
        this.items = items
    }
}