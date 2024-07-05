package com.example.e_medib.features.aktivitas_feature.repository

import com.example.e_medib.data.Resource
import com.example.e_medib.features.aktivitas_feature.model.DataCreateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.model.DataUpdateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.model.response.getAllAktivitasPengguna.AktivitasPenggunaResponse
import com.example.e_medib.features.aktivitas_feature.model.response.getAllAktivitasPengguna.GetAllAktivitasPenggunaResponse
import com.example.e_medib.features.aktivitas_feature.model.response.getAllDaftarAktivitas.GetAllDaftarAktivitasResponse
import com.example.e_medib.network.EMedibApi
import javax.inject.Inject

class AktivitasRepository @Inject constructor(private val eMedibApi: EMedibApi) {

    // GET ALL AKTIVITAS
    suspend fun getDaftarAktivitas(
        headers: Map<String, String>, tingkatAktivitas: String
    ): Resource<GetAllDaftarAktivitasResponse> {
        Resource.Loading(data = true)
        return try {
            val dataAllAktivitas = eMedibApi.getAllDaftarAktivitas(headers, tingkatAktivitas)
            Resource.Success(data = dataAllAktivitas)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // ============ AKTIVITAS PENGGUNA
    suspend fun getAllAktivitasPengguna(
        headers: Map<String, String>, tingkatAktivitas: String = "", tanggal: String
    ): Resource<GetAllAktivitasPenggunaResponse> {
        Resource.Loading(data = true)
        return try {
            val data = eMedibApi.getAllAktivitasPengguna(headers, tingkatAktivitas, tanggal)
            Resource.Success(data = data)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // Tambah AKtivitas
    suspend fun tabmbahAktivitasPengguna(
        headers: Map<String, String>, data: DataCreateAktivitasPenggunaModel
    ): Resource<AktivitasPenggunaResponse> {
        Resource.Loading(data = true)
        return try {
            val data = eMedibApi.tabmbahAktivitasPengguna(headers, data)
            Resource.Success(data = data)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // UPDATE AKTIVITAS PENGGUNA
    suspend fun editAktivitasPengguna(
        headers: Map<String, String>, id: Int, data: DataUpdateAktivitasPenggunaModel
    ): Resource<AktivitasPenggunaResponse> {
        Resource.Loading(data = true)
        return try {
            val data = eMedibApi.editAktivitasPengguna(headers, id, data)
            Resource.Success(data = data)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // DELETE AKTIVITAS PENGGUNA
    suspend fun deleteAktivitasPengguna(
        headers: Map<String, String>, id: Int
    ): Resource<Any> {
        Resource.Loading(data = true)
        return try {
            val data = eMedibApi.deleteAktivitasPengguna(headers, id)
            Resource.Success(data = data)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }


}