package com.example.e_medib.features.auth_feature.repository

import com.example.e_medib.data.Resource
import com.example.e_medib.features.auth_feature.model.DataRegisterModel
import com.example.e_medib.features.auth_feature.model.LoginModel
import com.example.e_medib.features.auth_feature.model.response.loginResponse.LoginModelResponse
import com.example.e_medib.features.auth_feature.model.response.registerResponse.RegisterResponse
import com.example.e_medib.network.EMedibApi
import javax.inject.Inject

class AuthRepository @Inject constructor(private val eMedibApi: EMedibApi) {

    // LOGIN
    suspend fun doLogin(data: LoginModel): Resource<LoginModelResponse> {
        Resource.Loading(data = true)
        return try {
            val loginResult = eMedibApi.doLogin(data)
            Resource.Success(data = loginResult)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // REGISTER
    suspend fun doRegister(data: DataRegisterModel): Resource<RegisterResponse> {
        Resource.Loading(data = true)
        return try {
            val registerResult = eMedibApi.doRegister(data)
            Resource.Success(data = registerResult)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    // UPDATE PROFILE
    suspend fun updateProfile(
        headers: Map<String, String>,
        data: DataRegisterModel
    ): Resource<RegisterResponse> {
        Resource.Loading(data = true)
        return try {
            val response = eMedibApi.updateProfile(headers, data)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

}