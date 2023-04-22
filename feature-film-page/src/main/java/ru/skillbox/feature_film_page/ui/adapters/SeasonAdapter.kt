package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.feature_film_page.models.TranslatableSeason
import ru.skillbox.feature_film_page.ui.viewholders.SeasonViewHolder

class SeasonAdapter(
    private val title: String,
    private val allButtonTitle: String,
    private val onClickAllButton: (List<TranslatableSeason>) -> Unit,
    private val onItemClick: (TranslatableSeason) -> Unit
) : RecyclerView.Adapter<SeasonViewHolder>() {

    private var items: List<TranslatableSeason>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeasonViewHolder {
        val binding =
            CollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonViewHolder(title, allButtonTitle, onClickAllButton, onItemClick, binding)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        if (items == null) {
            return
        }
        holder.bind(items!!)
    }

    fun setSeasons(seasons: List<TranslatableSeason>) {
        this.items = seasons
    }
}