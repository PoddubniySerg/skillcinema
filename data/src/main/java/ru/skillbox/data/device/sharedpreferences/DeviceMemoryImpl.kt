package ru.skillbox.data.device.sharedpreferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.skillbox.data.DataApp
import ru.skillbox.data.R
import ru.skillbox.data.repositories.interfaces.DeviceMemory
import javax.inject.Inject

class DeviceMemoryImpl @Inject constructor() : DeviceMemory {

    private val isFirstAppLaunchKey =
        DataApp.getContext().resources.getString(R.string.is_first_app_launch_key)
    private val store = SharedPreferences.dataStore()

    override suspend fun getIsFirstAppLaunchData(): Boolean {
        var isLaunched: Boolean? = null
        CoroutineScope(Dispatchers.IO).launch {
            store.data.collect { preferences ->
                isLaunched =
                    preferences[booleanPreferencesKey(isFirstAppLaunchKey)]
                cancel()
            }
        }.join()
        return isLaunched!!
    }

    override suspend fun saveIsFirstAppLaunchData(data: Boolean) {
        store.edit { preferences ->
            preferences[booleanPreferencesKey(isFirstAppLaunchKey)] = data
        }
    }
}