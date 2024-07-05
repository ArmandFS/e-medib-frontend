package com.example.e_medib.features.profile_feature.repository

import android.util.Log
import com.example.e_medib.data.Resource
import com.example.e_medib.features.home_feature.model.diary.DataDiaryModel
import com.example.e_medib.features.home_feature.model.diary.response.DiaryResponse
import com.example.e_medib.features.home_feature.model.diary.response.GetAllDiaryResponse
import com.example.e_medib.features.pantau_kalori_feature.model.DataKonsumsiMakananModel
import com.example.e_medib.features.pantau_kalori_feature.model.getAll.GetAllKonsumsiMakananResponse
import com.example.e_medib.features.pantau_kalori_feature.model.getAll.KonsumsiMakananResponse
import com.example.e_medib.features.profile_feature.model.bmiModel.BMIResponse
import com.example.e_medib.features.profile_feature.model.bmiModel.DataBMIModel
import com.example.e_medib.features.profile_feature.model.bmiModel.GetAllBMIResponse
import com.example.e_medib.features.profile_feature.model.bmrModel.BMRResponse
import com.example.e_medib.features.profile_feature.model.bmrModel.DataBMRModel
import com.example.e_medib.features.profile_feature.model.bmrModel.GetAllBMRResponse
import com.example.e_medib.features.profile_feature.model.logoutModel.LogoutModelResponse
import com.example.e_medib.network.EMedibApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val eMedibApi: EMedibApi) {
    // DO LOGOUT
    suspend fun doLogout(headers: Map<String, String>): Resource<LogoutModelResponse> {
        Resource.Loading(data = true)
        return try {
            val dataLogout = eMedibApi.doLogout(headers)
            Log.d("repo logout", "$dataLogout")
            Resource.Success(data = dataLogout)
        } catch (e: Exception) {
            Log.d("repo logout error", "$e")
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // GET ALL BMI
    suspend fun getAllBMI(
        headers: Map<String, String>
    ): Resource<GetAllBMIResponse> {
        Resource.Loading(data = true)
        return try {
            val response = eMedibApi.getAllBMI(headers)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // POST BMI
    suspend fun hitungBMI(
        data: DataBMIModel,
        headers: Map<String, String>
    ): Resource<BMIResponse> {
        Resource.Loading(data = true)
        return try {
            val response = eMedibApi.hitungBMI(data, headers)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // GET ALL BMR
    suspend fun getAllBMR(
        headers: Map<String, String>
    ): Resource<GetAllBMRResponse> {
        Resource.Loading(data = true)
        return try {
            val response = eMedibApi.getAllBMR(headers)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // POST BMI
    suspend fun hitungBMR(
        data: DataBMRModel,
        headers: Map<String, String>
    ): Resource<BMRResponse> {
        Resource.Loading(data = true)
        return try {
            val response = eMedibApi.hitungBMR(data, headers)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // DIARY REKAP
    suspend fun getAllDiaryRekap(
        headers: Map<String, String>,
    ): Resource<GetAllDiaryResponse> {
        Resource.Loading(data = true)
        return try {
            val response = eMedibApi.getAllDiaryRekap(headers)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }
}