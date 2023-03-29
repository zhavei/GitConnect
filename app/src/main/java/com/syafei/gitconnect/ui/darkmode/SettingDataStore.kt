package com.syafei.gitconnect.ui.darkmode

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.syafei.gitconnect.core.data.source.localdatabase.preference.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingDataStore @Inject constructor(@ApplicationContext context: Context) {
    companion object {
        private const val DATA_STORE_NAME = "setting_dark_mode.pref"
        private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    private val appContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = appContext.dataStore

    suspend fun setDarkMode(uiMode: UIMode) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when (uiMode) {
                UIMode.LIGHT -> false
                UIMode.DARK -> true
            }
        }
    }

    fun getDarkMode(): Flow<UIMode> = dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        when (preferences[IS_DARK_MODE] ?: false) {
            true -> UIMode.DARK
            false -> UIMode.LIGHT
        }
    }

    val uIModeFLow: Flow<UIMode> = dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        when (preferences[IS_DARK_MODE] ?: false) {
            true -> UIMode.DARK
            false -> UIMode.LIGHT
        }
    }


}