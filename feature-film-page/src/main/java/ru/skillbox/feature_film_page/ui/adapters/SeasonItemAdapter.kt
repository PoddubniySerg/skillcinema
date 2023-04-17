package ru.skillbox.feature_film_page.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.databinding.FilmPageTvSeriesItemBinding
import ru.skillbox.feature_film_page.models.TranslatableSeason

class SeasonItemAdapter(
    private val onItemClick: (TranslatableSeason) -> Unit
) : ListAdapter<TranslatableSeason, SeasonViewHolder>(SeasonDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(
            FilmPageTvSeriesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            val number = item.number
            val seriesCount = item.episodes.size
            seasonDescription.text = getItemText(root.resources, number, seriesCount)
        }
    }

    private fun getItemText(resources: Resources, number: Int, seriesCount: Int): String {
        return String.format(
            resources.getString(R.string.series_seasons_item),
            number,
            seriesCount
        )
    }
}

class SeasonViewHolder(val binding: FilmPageTvSeriesItemBinding) :
    RecyclerView.ViewHolder(binding.root)

class SeasonDiffUtil : DiffUtil.ItemCallback<TranslatableSeason>() {

    override fun areItemsTheSame(
        oldItem: TranslatableSeason,
        newItem: TranslatableSeason
    ): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(
        oldItem: TranslatableSeason,
        newItem: TranslatableSeason
    ): Boolean {
        return oldItem.filmId == newItem.filmId
    }
}