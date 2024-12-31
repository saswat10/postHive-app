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
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton


class DataStorage(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(context.packageName)

    suspend fun saveBearerToken(bearerToken: String) {
        context.dataStore.edit {
            it[bearerTokenKey] = bearerToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit {
            it[refreshTokenKey] = refreshToken
        }
    }

    suspend fun getBearerToken(): String? {
        return context.dataStore.data.firstOrNull()?.get(bearerTokenKey)
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.firstOrNull()?.get(refreshTokenKey)
    }

    private val bearerTokenKey = stringPreferencesKey("bearer_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
}

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStorage = DataStorage(context)
}