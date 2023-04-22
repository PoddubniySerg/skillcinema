package ru.skillbox.feature_film_page.ui.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.ui.viewholders.CollectionViewHolder
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.models.TranslatableStaffItem
import ru.skillbox.feature_film_page.ui.adapters.StaffItemAdapter

abstract class AbstractStaffViewHolder(
    private val title: String,
    private val onClickAllButton: (List<TranslatableStaffItem>) -> Unit,
    private val onItemClick: (TranslatableStaffItem) -> Unit,
    private val maxItemsViewed: Int,
    private val spanCount: Int,
    binding: CollectionBinding
) : CollectionViewHolder<List<TranslatableStaffItem>>(binding, dpListTopMargin = 32, dpRootTopMargin = 40) {

    private lateinit var adapter: StaffItemAdapter

    init {
        with(binding) {
            nameCollection.text = title
            setManager(collection)
            setAdapter(collection)
        }
    }

    override fun bind(item: List<TranslatableStaffItem>) {
        val limitedItems = item.take(maxItemsViewed)
        with(binding) {
            setTextAllButton(buttonShowAll, item)
            buttonShowAll.setOnClickListener { onClickAllButton(item) }
            adapter.submitList(limitedItems)
        }
    }

    private fun setManager(collectionItems: RecyclerView) {
        val manager =
            GridLayoutManager(collectionItems.context, spanCount, RecyclerView.HORIZONTAL, false)
        collectionItems.layoutManager = manager
    }

    private fun setTextAllButton(button: AppCompatButton, items: List<TranslatableStaffItem>) {
        if (items.size < maxItemsViewed) {
            button.visibility = View.GONE
            return
        }
        button.visibility = View.VISIBLE
        val iconId = R.drawable.film_page_show_all_button_icon
        val icon = ResourcesCompat.getDrawable(button.resources, iconId, null)
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
        button.text = items.size.toString()
    }

    private fun setAdapter(collectionItems: RecyclerView) {
        adapter = StaffItemAdapter(onItemClick)
        collectionItems.adapter = adapter
    }
}