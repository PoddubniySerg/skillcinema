package ru.skillbox.data.di

import dagger.Module
import dagger.Provides
import ru.skillbox.data.api.CinemaApiImpl
import ru.skillbox.data.api.CinemaApiSource
import ru.skillbox.data.api.KinopoiskApi
import ru.skillbox.data.device.sharedpreferences.DeviceMemoryImpl
import ru.skillbox.data.repositories.HomeRepositoryImpl
import ru.skillbox.data.repositories.ListPageMoviesRepository
import ru.skillbox.data.repositories.LoaderRepositoryImpl
import ru.skillbox.data.repositories.OnboardingRepositoryImpl
import ru.skillbox.data.repositories.interfaces.CinemaApi
import ru.skillbox.data.repositories.interfaces.DeviceMemory
import ru.skillbox.feature_home.domain.HomeRepository
import ru.skillbox.feature_list.domain.repositories.MoviesRepository
import ru.skillbox.feature_onboarding.domain.repositories.LoaderRepository
import ru.skillbox.feature_onboarding.domain.repositories.OnboardingRepository
import javax.inject.Scope

@Module
class DataModule {

    @Provides
    @DataScope
    fun deviceMemoryProvide(deviceMemoryImpl: DeviceMemoryImpl): DeviceMemory {
        return deviceMemoryImpl
    }

    @Provides
    @DataScope
    fun provideOnBoardingRepositoryProvide(
        onboardingRepository: OnboardingRepositoryImpl
    ): OnboardingRepository {
        return onboardingRepository
    }

    @Provides
    @DataScope
    fun provideKinopoiskApi(apiSource: CinemaApiSource): KinopoiskApi {
        return apiSource.getApi()
    }

    @Provides
    @DataScope
    fun provideCinemaApi(apiImpl: CinemaApiImpl): CinemaApi {
        return apiImpl
    }

    @Provides
    @DataScope
    fun provideLoaderRepository(loaderRepository: LoaderRepositoryImpl): LoaderRepository {
        return loaderRepository
    }

    @Provides
    @DataScope
    fun provideHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository {
        return homeRepository
    }

    @Provides
    @DataScope
    fun provideListPageMoviesRepository(
        listPageMoviesRepository: ListPageMoviesRepository
    ): MoviesRepository {
        return listPageMoviesRepository
    }
}

@Scope
annotation class DataScope