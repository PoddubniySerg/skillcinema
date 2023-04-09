package ru.skillbox.data.repositories

import ru.skillbox.data.repositories.interfaces.DeviceMemory
import ru.skillbox.feature_onboarding.domain.repositories.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val deviceMemory: DeviceMemory
) :
    OnboardingRepository {

    override suspend fun isAppWasLaunch(): Boolean {
        return deviceMemory.getIsFirstAppLaunchData()
    }

    override suspend fun setOnBoardingLaunched() {
        deviceMemory.saveIsFirstAppLaunchData(false)
    }
}