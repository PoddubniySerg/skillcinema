package ru.skillbox.feature_list.models

import ru.skillbox.core.domain.models.MoviesCollectionTypes
import ru.skillbox.core.domain.models.MoviesFilter

class GetMoviesByPageParams(val moviesType: MoviesCollectionTypes?, val filter: MoviesFilter?)