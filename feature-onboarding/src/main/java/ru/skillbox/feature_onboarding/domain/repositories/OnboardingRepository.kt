package ru.skillbox.feature_onboarding.domain.repositories

interface OnboardingRepository {

    suspend fun isAppWasLaunch(): Boolean

    suspend fun setOnBoardingLaunched()
}