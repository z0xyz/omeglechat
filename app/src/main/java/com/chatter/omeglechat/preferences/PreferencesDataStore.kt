package com.chatter.omeglechat.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences",
    produceMigrations = {
        emptyList()
    },
    corruptionHandler = null,
    scope = CoroutineScope(Dispatchers.IO)
)

class PreferencesDataStore(
    val context: Context
) {

    // todo: other types of data structures aren't supported as of now for some preferences e.g., language, common interests, just because of my current lack of knowledge
    companion object {
        val ENABLE_NOTIFICATIONS = booleanPreferencesKey(name = "enable_notifications")
        val ENABLE_DARK_MODE = booleanPreferencesKey(name = "enable_dark_mode")
        val COMMON_INTERESTS = stringPreferencesKey(name = "common_interests")
        val ENABLE_LANGUAGE_MATCH = booleanPreferencesKey(name = "enable_language_match")
        val AGE = intPreferencesKey(name = "user_age")
        val AUTO_REPLY = booleanPreferencesKey(name = "auto_reply")
    }

//    private object PreferenceKeys {
//        val commonInterests: Preferences.Key<String> = stringPreferencesKey("interests")
//    }

    fun getUserInterests(): Flow<List<String>> = context.dataStore.data.map { preferences ->
//        preferences[PreferenceKeys.commonInterests]?.split(",") ?: emptyList()
        preferences[COMMON_INTERESTS]?.split(", ") ?: emptyList()
    }
    suspend fun updateCommonInterests(commonInterests: String) = context.dataStore.edit {
        it[COMMON_INTERESTS] = commonInterests
    }

}
data class UserPreferences(
    val notifications: Boolean,
    val darkMode: Boolean,
    val languageMatch: Boolean,
    val language: String,
    val interests: String,
    val autoReply: Boolean
)