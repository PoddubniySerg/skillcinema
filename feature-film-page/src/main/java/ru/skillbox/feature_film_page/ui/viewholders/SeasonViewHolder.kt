package ru.skillbox.feature_film_page.ui.viewholders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.ui.viewholders.CollectionViewHolder
import ru.skillbox.feature_film_page.models.TranslatableSeason
import ru.skillbox.feature_film_page.ui.adapters.SeasonItemAdapter

class SeasonViewHolder(
    private val title: String,
    private val allButtonTitle: String,
    private val onClickAllButton: (List<TranslatableSeason>) -> Unit,
    private val onItemClick: (TranslatableSeason) -> Unit,
    binding: CollectionBinding
) : CollectionViewHolder<List<TranslatableSeason>>(binding, dpListTopMargin = 8, dpRootTopMargin = 40) {

    private lateinit var adapter: SeasonItemAdapter

    init {
        with(binding) {
            nameCollection.text = title
            buttonShowAll.text = allButtonTitle
            setManager(collection)
            setAdapter(collection)
        }
    }

    override fun bind(item: List<TranslatableSeason>) {
        with(binding) {
            buttonShowAll.setOnClickListener { onClickAllButton(item) }
            adapter.submitList(item)
        }
    }

    private fun setManager(collectionItems: RecyclerView) {
        collectionItems.layoutManager =
            LinearLayoutManager(collectionItems.context, RecyclerView.VERTICAL, false)
    }

    private fun setAdapter(collectionItems: RecyclerView) {
        adapter = SeasonItemAdapter(onItemClick)
        collectionItems.adapter = adapter
    }
}