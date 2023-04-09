package ru.skillbox.feature_home.domain

import ru.skillbox.core.domain.usecases.GetMoviesCollectionsUseCase
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    homeRepository: HomeRepository
) : GetMoviesCollectionsUseCase(homeRepository)