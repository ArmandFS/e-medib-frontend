package com.example.e_medib.features.pantau_kalori_feature.repository

import com.example.e_medib.data.Resource
import com.example.e_medib.features.pantau_kalori_feature.model.DataKonsumsiMakananModel
import com.example.e_medib.features.pantau_kalori_feature.model.getAll.GetAllKonsumsiMakananResponse
import com.example.e_medib.features.pantau_kalori_feature.model.getAll.KonsumsiMakananResponse
import com.example.e_medib.network.EMedibApi
import javax.inject.Inject

class PantauKaloriRepository @Inject constructor(private val eMedibApi: EMedibApi) {

    // GET ALL KONSUMSI MAKANAN
    suspend fun getAllKonsumsiMakanan(
        waktuMakan: String,
        tanggal: String,
        headers: Map<String, String>
    ): Resource<GetAllKonsumsiMakananResponse> {
        Resource.Loading(data = true)
        return try {
            val dataAllKonsumsiMakanan =
                eMedibApi.getAllKonsumsiMakanan(waktuMakan, tanggal, headers)
            Resource.Success(data = dataAllKonsumsiMakanan)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // TAMBAH KONSUMSI MAKANAN
    suspend fun tambahKonsumsiMakanan(
        data: DataKonsumsiMakananModel,
        headers: Map<String, String>
    ): Resource<KonsumsiMakananResponse> {
        Resource.Loading(data = true)
        return try {
            val response = eMedibApi.tambahKonsumsiMakanan(data, headers)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }
}