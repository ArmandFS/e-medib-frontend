package com.example.e_medib.features.auth_feature.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_medib.data.Resource
import com.example.e_medib.features.auth_feature.model.DataRegisterModel
import com.example.e_medib.features.auth_feature.model.LoginModel
import com.example.e_medib.features.auth_feature.repository.AuthRepository
import com.example.e_medib.utils.CustomDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)


    // LOGIN
    fun doLogin(data: LoginModel, context: Context, navigate: () -> Unit) {
        val localStorage = CustomDataStore(context)

        viewModelScope.launch {
            isLoading = true

            try {
                when (val response = authRepository.doLogin(data)) {
                    is Resource.Success -> {
                        response.data?.access_token?.let { Log.d("Login Success", it.token) }
                        Toast.makeText(
                            context, "Berhasil login, mohon tunggu",
                            Toast.LENGTH_SHORT
                        ).show()
                        // SAVE TOKEN TO LOCAL STORAGE
                        CoroutineScope(Dispatchers.IO).launch {
                            response.data?.access_token?.let { localStorage.saveToken(it.token) }
                            localStorage.deleteTempDataDiary()
                        }
                        delay(500L)
                        navigate()
                    }
                    is Resource.Error -> {
                        Log.d("Login", "${response.message}")
                        Toast.makeText(
                            context, "Login gagal, coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    else -> {
                        Log.d("Login", "${response}")
                    }
                }

            } catch (e: Exception) {
                Log.d("Login Error", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    // REGISTER
    fun doRegister(data: DataRegisterModel, context: Context, navigate: () -> Unit) {
        viewModelScope.launch {
            isLoading = true

            try {
                when (val response = authRepository.doRegister(data)) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil membuat akun, mohon login",
                            Toast.LENGTH_SHORT
                        ).show()

                        delay(1000L)
                        navigate()
                    }
                    is Resource.Error -> {
                        Log.d("Register", "${response.message}")
                        Toast.makeText(
                            context, "Register gagal, coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    else -> {
                        Log.d("Register", "${response}")
                    }
                }

            } catch (e: Exception) {
                Log.d("Register Error", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    // UPDATE USER
    fun updateProfile(
        headers: Map<String, String>,
        data: DataRegisterModel,
        context: Context,
        navigate: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            try {
                when (val response = authRepository.updateProfile(headers, data)) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil memperbarui data",
                            Toast.LENGTH_SHORT
                        ).show()

                        delay(1000L)
                        navigate()
                    }
                    is Resource.Error -> {
                        Log.d("updateProfile", "${response.data}")
                        Toast.makeText(
                            context, "gagal Memperbarui data, coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    else -> {
                        Log.d("updateProfile", "${response}")
                    }
                }

            } catch (e: Exception) {
                Log.d("Register Error", "$e")

            } finally {
                isLoading = false
            }
        }
    }


}