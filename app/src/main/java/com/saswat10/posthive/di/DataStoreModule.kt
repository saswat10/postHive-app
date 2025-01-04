package com.saswat10.posthive.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStorage(private val context: Context) {

    private val bearerTokenKey = stringPreferencesKey("bearer_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")

    suspend fun saveBearerToken(bearerToken: String) {
        context.dataStore.edit { preferences ->
            preferences[bearerTokenKey] = bearerToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[refreshTokenKey] = refreshToken
        }
    }

    suspend fun getBearerToken(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[bearerTokenKey] }
            .first()
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[refreshTokenKey] }
            .first()
    }

    suspend fun removeBearerToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(bearerTokenKey)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStorage = DataStorage(context)
}
