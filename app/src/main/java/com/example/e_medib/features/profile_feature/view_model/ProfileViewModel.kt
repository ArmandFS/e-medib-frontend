package com.example.e_medib.features.profile_feature.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_medib.data.Resource
import com.example.e_medib.features.home_feature.model.diary.response.GetAllDiaryResponse
import com.example.e_medib.features.home_feature.model.hba1c.DataHba1cModel
import com.example.e_medib.features.home_feature.model.hba1c.getAll.GetAllHba1cResponse
import com.example.e_medib.features.profile_feature.model.bmiModel.BMIResponse
import com.example.e_medib.features.profile_feature.model.bmiModel.DataBMIModel
import com.example.e_medib.features.profile_feature.model.bmiModel.GetAllBMIResponse
import com.example.e_medib.features.profile_feature.model.bmrModel.BMRResponse
import com.example.e_medib.features.profile_feature.model.bmrModel.DataBMRModel
import com.example.e_medib.features.profile_feature.repository.ProfileRepository
import com.example.e_medib.utils.CustomDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profieRepository: ProfileRepository
) : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)

    var allDiaryRekapData: GetAllDiaryResponse by mutableStateOf(GetAllDiaryResponse(data = listOf()))

    var recentBMIData: BMIResponse by mutableStateOf(BMIResponse())
    var recentBMRData: BMRResponse by mutableStateOf(BMRResponse())

    fun doLogout(headers: Map<String, String>, context: Context, navigate: () -> Unit) {
        val localStorage = CustomDataStore(context)

        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = profieRepository.doLogout(headers)) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil logout, mohon tunggu",
                            Toast.LENGTH_SHORT
                        ).show()

                        // DELETE TOKEN
                        CoroutineScope(Dispatchers.IO).launch {
                            localStorage.deleteToken()
                        }
                        delay(1000L)
                        navigate()
                        Log.d("Logout sukses", "${response.data?.meta}")

                    }
                    is Resource.Error -> {
                        Log.d("Logout gagal", "${response.data}")
                        Toast.makeText(
                            context, "Proses gagal, coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Log.d("Logout", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Logout exception", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    fun getAllBMI(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = profieRepository.getAllBMI(headers)) {
                    is Resource.Success -> {
                        recentBMIData = response.data!!.data.first()
                    }
                    is Resource.Error -> {
                        Log.d("Error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error Catch", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun hitungBMI(
        data: DataBMIModel,
        headers: Map<String, String>
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = profieRepository.hitungBMI(data, headers)) {
                    is Resource.Success -> {
                        getAllBMI(headers)
                    }
                    is Resource.Error -> {
                        Log.d("Error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error Catch", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun getAllBMR(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = profieRepository.getAllBMR(headers)) {
                    is Resource.Success -> {
                        recentBMRData = response.data!!.data.first()
                    }
                    is Resource.Error -> {
                        Log.d("Error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error Catch", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun hitungBMR(
        data: DataBMRModel,
        headers: Map<String, String>
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = profieRepository.hitungBMR(data, headers)) {
                    is Resource.Success -> {
                        getAllBMR(headers)
                    }
                    is Resource.Error -> {
                        Log.d("Error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error Catch", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun getAllDiaryRekap(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = profieRepository.getAllDiaryRekap(headers)) {
                    is Resource.Success -> {
                        allDiaryRekapData = response.data!!
                    }
                    is Resource.Error -> {
                        Log.d("Error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error Catch", "$e")
            } finally {
                isLoading = false
            }
        }
    }

}