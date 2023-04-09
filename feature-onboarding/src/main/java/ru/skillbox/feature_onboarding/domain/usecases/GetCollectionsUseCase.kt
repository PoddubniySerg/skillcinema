package ru.skillbox.feature_onboarding.domain.usecases

import ru.skillbox.core.domain.usecases.GetMoviesCollectionsUseCase
import ru.skillbox.feature_onboarding.domain.repositories.LoaderRepository
import javax.inject.Inject

open class GetCollectionsUseCase @Inject constructor(
    loaderRepository: LoaderRepository
) : GetMoviesCollectionsUseCase(loaderRepository)