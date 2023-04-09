package ru.skillbox.core.ui.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.core.R
import ru.skillbox.core.databinding.MoviesItemMovieBinding
import ru.skillbox.core.domain.entities.GenreString
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.domain.entities.TranslatableName
import ru.skillbox.core.utils.TranslatableNameProcessor

open class MovieViewHolder(private val binding: MoviesItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, onItemClick: (Movie) -> Unit) {
        with(binding) {

            setSeenIcon(
                seen = movie.seen,
                seenIcon = seenIcon,
                posterView = poster,
                movie = movie
            )

            TranslatableNameProcessor.setName(movie as TranslatableName, nameMovie)

            if (movie is MainMovie) {
                setRating(
                    rating = movie.rating,
                    rateView = rating
                )
                setGenres(movie.genres, genresMovie)
            } else {
                rating.visibility = View.GONE
            }

            root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    private fun setSeenIcon(
        seen: Boolean,
        seenIcon: View,
        posterView: AppCompatImageView,
        movie: Movie
    ) {
        val placeHolderId =
            if (seen) {
                seenIcon.visibility = View.VISIBLE
                R.drawable.movies_item_poster_background_place_holder_seen
            } else {
                seenIcon.visibility = View.GONE
                R.drawable.movies_item_poster_background_place_holder_not_seen
            }
        Glide.with(posterView)
            .load(movie.posterUrlPreview ?: return)
            .placeholder(placeHolderId)
            .into(posterView)
    }

    private fun setRating(rating: String?, rateView: AppCompatTextView) {
        if (rating == null) rateView.visibility = View.GONE
        else {
            rateView.text = rating
            rateView.visibility = View.VISIBLE
        }
    }

    private fun setGenres(genres: List<GenreString>?, genreView: AppCompatTextView) {
        if (genres == null) genreView.visibility = View.GONE
        else {
            genreView.text =
                genres.mapNotNull { genre -> genre.genre }.joinToString(", ")
        }
    }
}