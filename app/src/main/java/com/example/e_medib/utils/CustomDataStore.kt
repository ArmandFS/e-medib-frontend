package com.example.e_medib.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.e_medib.features.home_feature.model.gulaDarah.DataGulaDarahModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CustomDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")

        // Keperluan Tambah Diary
        private val GULA_DARAH = stringPreferencesKey("gula_darah")
        private val KOLESTEROL = stringPreferencesKey("kolesterol")
        private val GAMBAR_LUKA = stringPreferencesKey("gambar_luka")
        private val CATATAN_LUKA = stringPreferencesKey("catatan_luka")
        private val TOTAL_KONSUMSI_KALORI = stringPreferencesKey("total_konsumsi_kalori")
        private val TOTAL_KALORI_AKITIVITAS = stringPreferencesKey("total_kalori_aktivitas")
        private val CATATAN_LAINNYA = stringPreferencesKey("catatan_lainnya")

    }

    val getAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_TOKEN_KEY] ?: ""
    }

    // KEPELUAN TAMBAH DIARY
    val getGulaDarah: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[GULA_DARAH] ?: ""
    }
    val getKolesterol: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KOLESTEROL] ?: ""
    }
    val getGambarLuka: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[GAMBAR_LUKA] ?: ""
    }
    val getCatatanLuka: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[CATATAN_LUKA] ?: ""
    }
    val getTotalKonsumsiKalori: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[TOTAL_KONSUMSI_KALORI] ?: ""
    }
    val getTotalKaloriAktivitas: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[TOTAL_KALORI_AKITIVITAS] ?: ""
    }
    val getCatatanLainnya: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[CATATAN_LAINNYA] ?: ""
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    // KEPERLUAN TAMBAH DIARY
    suspend fun saveGulaDarah(data: String) {
        context.dataStore.edit { preferences ->
            preferences[GULA_DARAH] = data
        }
    }

    suspend fun saveKolesterol(data: String) {
        context.dataStore.edit { preferences ->
            preferences[KOLESTEROL] = data
        }
    }

    suspend fun saveGambarLuka(data: String) {
        context.dataStore.edit { preferences ->
            preferences[GAMBAR_LUKA] = data
        }
    }

    suspend fun saveCatatanLuka(data: String) {
        context.dataStore.edit { preferences ->
            preferences[CATATAN_LUKA] = data
        }
    }

    suspend fun saveCatatanLainnya(data: String) {
        context.dataStore.edit { preferences ->
            preferences[CATATAN_LAINNYA] = data
        }
    }

    suspend fun saveTotalKonsumsiKalori(data: String) {
        context.dataStore.edit { preferences ->
            preferences[TOTAL_KONSUMSI_KALORI] = data
        }
    }

    suspend fun saveTotalKaloriAktivitas(data: String) {
        context.dataStore.edit { preferences ->
            preferences[TOTAL_KALORI_AKITIVITAS] = data
        }
    }


    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
        }
    }

    suspend fun deleteTempDataDiary() {
        context.dataStore.edit { preferences ->
            preferences.remove(GULA_DARAH)
        }
        context.dataStore.edit { preferences ->
            preferences.remove(KOLESTEROL)
        }
        context.dataStore.edit { preferences ->
            preferences.remove(GAMBAR_LUKA)
        }
        context.dataStore.edit { preferences ->
            preferences.remove(CATATAN_LUKA)
        }
        context.dataStore.edit { preferences ->
            preferences.remove(TOTAL_KONSUMSI_KALORI)
        }
        context.dataStore.edit { preferences ->
            preferences.remove(TOTAL_KALORI_AKITIVITAS)
        }
        context.dataStore.edit { preferences ->
            preferences.remove(CATATAN_LAINNYA)
        }
    }
}