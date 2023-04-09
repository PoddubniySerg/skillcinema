package ru.skillbox.data.repositories.interfaces

interface DeviceMemory {

    suspend fun getIsFirstAppLaunchData(): Boolean

    suspend fun saveIsFirstAppLaunchData(data: Boolean)
}