package com.syafei.gitconnect.core.data.source.localdatabase.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserThemePreferences @Inject constructor(@ApplicationContext context: Context) {

    private val themeDataStore = context.dataStore
    private val preferencesKey = booleanPreferencesKey("user_theme_setting")

    fun getThemePreference(): Flow<Boolean> {

        return themeDataStore.data.map {
            it[preferencesKey] ?: false
        }
    }

    suspend fun saveThemePreference(darkMode: Boolean) {
        themeDataStore.edit {
            it[preferencesKey] = darkMode
        }
    }


}