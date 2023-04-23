package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.feature_film_page.models.TranslatableMoviesCollection
import ru.skillbox.feature_film_page.ui.viewholders.RelatedMoviesViewHolder

class RelatedMoviesAdapter(
    private val title: String,
    private val onItemClick: (Movie) -> Unit,
    private val onClickAllButton: (TranslatableMoviesCollection) -> Unit,
) : RecyclerView.Adapter<RelatedMoviesViewHolder>() {

    private var collection: TranslatableMoviesCollection? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedMoviesViewHolder {
        val binding =
            CollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RelatedMoviesViewHolder(title, onItemClick, onClickAllButton, binding)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RelatedMoviesViewHolder, position: Int) {
        if (collection == null) {
            return
        }
        holder.bind(collection!!)
    }

    fun setCollection(collection: TranslatableMoviesCollection) {
        this.collection = collection
    }
}