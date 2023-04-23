package ru.skillbox.core.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.core.R
import ru.skillbox.core.databinding.MoviesItemMovieBinding
import ru.skillbox.core.databinding.MoviesItemShowAllBinding
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.ui.viewholders.MovieViewHolder

class MovieItemAdapter(
    private val allButtonIcon: Drawable?,
    private val allButtonText: String?,
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtilCallback()) {

    private var onClickAllButton: (() -> Unit)? = null

    companion object {
        fun verticalDividerItemDecoration(context: Context): List<DividerItemDecoration> {
            val verticalDividerItemDecoration =
                DividerItemDecoration(context, RecyclerView.HORIZONTAL)
            verticalDividerItemDecoration.setDrawable(
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.movies_item_divider,
                    context.theme
                )!!
            )
            return listOf(verticalDividerItemDecoration)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            R.layout.movies_item_movie -> {
                return MovieViewHolder(
                    MoviesItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                return ShowAllViewHolder(
                    MoviesItemShowAllBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> inflateMovie(position, holder)
            is ShowAllViewHolder -> inflateShowAllItem(holder.binding)
        }
    }

    override fun getItemCount(): Int {
        val additionalElementsCount =
            if (allButtonIcon == null) {
                0
            } else {
                1
            }
        return super.getItemCount() + additionalElementsCount
    }

    override fun getItemViewType(position: Int): Int {
        val lastIndexItems =
            if (allButtonIcon == null) {
                itemCount
            } else {
                itemCount - 1
            }
        return if (position < lastIndexItems) {
            R.layout.movies_item_movie
        } else {
            R.layout.movies_item_show_all
        }
    }

    fun setOnClickAllButton(onClickAllButton: () -> Unit) {
        this.onClickAllButton = onClickAllButton
    }

    private fun inflateMovie(position: Int, holder: MovieViewHolder) {
        val movie = getItem(position)
        holder.bind(movie, onItemClick)
    }

    private fun inflateShowAllItem(binding: MoviesItemShowAllBinding) {
        if (onClickAllButton == null) {
            return
        }
        with(binding) {
            buttonShowAllMovies.setImageDrawable(allButtonIcon)
            showAllItemText.text = allButtonText
            root.setOnClickListener { onClickAllButton!!.invoke() }
        }
    }
}

class ShowAllViewHolder(val binding: MoviesItemShowAllBinding) :
    RecyclerView.ViewHolder(binding.root)

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}