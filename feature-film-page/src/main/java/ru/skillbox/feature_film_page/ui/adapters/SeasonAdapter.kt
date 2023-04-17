package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.feature_film_page.databinding.FilmPageCollectionItemBinding
import ru.skillbox.feature_film_page.models.TranslatableSeason

class SeasonAdapter(
    private val title: String,
    private val allButtonTitle: String,
    private val onClickAllButton: (List<TranslatableSeason>) -> Unit,
    private val onItemClick: (TranslatableSeason) -> Unit
) : RecyclerView.Adapter<FilmPageCollectionViewHolder>() {

    private var items: List<TranslatableSeason>? = null

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
        with(holder.binding) {
            if (items == null) {
                return
            }
            filmPageCollectionTitle.text = title
            filmPageAllButton.text = allButtonTitle
            filmPageAllButton.setOnClickListener { onClickAllButton(items!!) }
            setManager(filmPageCollectionItems)
            setAdapter(filmPageCollectionItems)
        }
    }

    private fun setManager(collectionItems: RecyclerView) {
        collectionItems.layoutManager =
            LinearLayoutManager(collectionItems.context, RecyclerView.VERTICAL, false)
    }

    private fun setAdapter(collectionItems: RecyclerView) {
        val adapter = SeasonItemAdapter(onItemClick)
        collectionItems.adapter = adapter
        adapter.submitList(items)
    }

    fun setSeasons(seasons: List<TranslatableSeason>) {
        this.items = seasons
    }
}