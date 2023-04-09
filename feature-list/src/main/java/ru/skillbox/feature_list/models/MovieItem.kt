package ru.skillbox.feature_list.models

import ru.skillbox.core.domain.entities.GenreString
import ru.skillbox.core.domain.entities.Movie
import ru.skillbox.core.dto.MovieDto

class MovieItem(
    genres: List<GenreString>?,
    rating: String?,
    id: Int,
    nameRu: String?,
    nameEn: String?,
    posterUrl: String?,
    posterUrlPreview: String?,
    seen: Boolean
) : ListItem, MovieDto(genres, rating, id, nameRu, nameEn, posterUrl, posterUrlPreview, seen) {

    override fun areItemsTheSame(newItem: ListItem): Boolean {
        return if (newItem is Movie) {
            this.id == newItem.id
        } else {
            false
        }
    }

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return if (newItem is Movie) {
            (this.id == newItem.id
                    && this.seen == newItem.seen
                    && this.nameEn == newItem.nameEn
                    && this.nameRu == newItem.nameRu
                    && this.posterUrl == newItem.posterUrl
                    && this.posterUrlPreview == newItem.posterUrlPreview)
        } else {
            false
        }
    }
}