package ru.skillbox.feature_onboarding.domain.usecases

import ru.skillbox.feature_onboarding.domain.repositories.OnboardingRepository
import javax.inject.Inject

class SetOnBoardingLaunchedUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    suspend fun execute() {
        onboardingRepository.setOnBoardingLaunched()
    }
}