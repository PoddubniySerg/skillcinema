package ru.skillbox.data.device.sharedpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import ru.skillbox.data.DataApp

object SharedPreferences {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "skillcinema_settings")
    fun dataStore() = DataApp.getContext().dataStore
}