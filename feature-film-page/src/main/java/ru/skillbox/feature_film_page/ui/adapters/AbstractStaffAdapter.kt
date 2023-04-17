package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.databinding.FilmPageCollectionItemBinding
import ru.skillbox.feature_film_page.models.TranslatableStaffItem

abstract class AbstractStaffAdapter(
    private val title: String,
    protected val onClickAllButton: (List<TranslatableStaffItem>) -> Unit,
    private val onItemClick: (TranslatableStaffItem) -> Unit
) : RecyclerView.Adapter<FilmPageCollectionViewHolder>() {

    protected abstract val maxItemsViewed: Int
    protected abstract val spanCount: Int
    private var items: List<TranslatableStaffItem>? = null
    private val dpListTopMargin = 32

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmPageCollectionViewHolder {
        return FilmPageCollectionViewHolder(
            FilmPageCollectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: FilmPageCollectionViewHolder, position: Int) {
        if (items == null) {
            return
        }
        val limitedItems = items!!.take(maxItemsViewed)
        with(holder.binding) {
            filmPageCollectionTitle.text = title
            setTextAllButton(filmPageAllButton)
            filmPageAllButton.setOnClickListener { onClickAllButton(items!!) }
            setMarginToRecyclerView(filmPageCollectionItems)
            setManager(filmPageCollectionItems)
            setAdapter(filmPageCollectionItems, limitedItems)
        }
    }

    private fun setMarginToRecyclerView(collectionItems: RecyclerView) {
        val dpRatio = collectionItems.resources.displayMetrics.density
        val pxTopMargin = dpListTopMargin * dpRatio
        (collectionItems.layoutParams as ConstraintLayout.LayoutParams).updateMargins(top = pxTopMargin.toInt())
    }

    fun setItems(items: List<TranslatableStaffItem>) {
        this.items = items
    }

    private fun setTextAllButton(button: AppCompatButton) {
        if (items == null || items!!.size < maxItemsViewed) {
            button.visibility = View.GONE
            return
        }
        button.visibility = View.VISIBLE
        val iconId = R.drawable.film_page_show_all_button_icon
        val icon = ResourcesCompat.getDrawable(button.resources, iconId, null)
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
        button.text = items!!.size.toString()
    }

    private fun setManager(collectionItems: RecyclerView) {
        val manager =
            GridLayoutManager(collectionItems.context, spanCount, RecyclerView.HORIZONTAL, false)
        collectionItems.layoutManager = manager
    }

    private fun setAdapter(
        collectionItems: RecyclerView,
        limitedItems: List<TranslatableStaffItem>
    ) {
        val adapter = StaffItemAdapter(onItemClick)
        collectionItems.adapter = adapter
        adapter.submitList(limitedItems)
    }
}