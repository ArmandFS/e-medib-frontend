package com.example.e_medib.features.pantau_kalori_feature.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_medib.data.Resource
import com.example.e_medib.features.pantau_kalori_feature.model.DataKonsumsiMakananModel
import com.example.e_medib.features.pantau_kalori_feature.model.getAll.GetAllKonsumsiMakananResponse
import com.example.e_medib.features.pantau_kalori_feature.repository.PantauKaloriRepository
import com.example.e_medib.utils.CustomDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PantauKaloriViewModel @Inject constructor(private val pantauKaloriRepository: PantauKaloriRepository) :
    ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)

    var allKonsumsiSarapan: GetAllKonsumsiMakananResponse by mutableStateOf(
        GetAllKonsumsiMakananResponse(data = listOf())
    )
    var allKonsumsiMakanSiang: GetAllKonsumsiMakananResponse by mutableStateOf(
        GetAllKonsumsiMakananResponse(data = listOf())
    )
    var allKonsumsiMakanMalam: GetAllKonsumsiMakananResponse by mutableStateOf(
        GetAllKonsumsiMakananResponse(data = listOf())
    )
    var allKonsumsiLainnya: GetAllKonsumsiMakananResponse by mutableStateOf(
        GetAllKonsumsiMakananResponse(data = listOf())
    )

    fun getSarapan(headers: Map<String, String>, tanggal: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response =
                    pantauKaloriRepository.getAllKonsumsiMakanan("SARAPAN", tanggal, headers)) {
                    is Resource.Success -> {
                        allKonsumsiSarapan = response.data!!
                        Log.d("Data Konsumsi", "${response.data}")
                    }
                    is Resource.Error -> {
                        Log.d("Data Konsumsi error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data Konsumsi", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Data Konsumsi exception", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    fun getMakanSiang(headers: Map<String, String>, tanggal: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response =
                    pantauKaloriRepository.getAllKonsumsiMakanan("MAKAN_SIANG", tanggal, headers)) {
                    is Resource.Success -> {
                        allKonsumsiMakanSiang = response.data!!
                        Log.d("Data Konsumsi", "${response.data}")
                    }
                    is Resource.Error -> {
                        Log.d("Data Konsumsi error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data Konsumsi", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Data Konsumsi exception", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    fun getMakanMalam(headers: Map<String, String>, tanggal: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response =
                    pantauKaloriRepository.getAllKonsumsiMakanan("MAKAN_MALAM", tanggal, headers)) {
                    is Resource.Success -> {
                        allKonsumsiMakanMalam = response.data!!
                        Log.d("Data Konsumsi", "${response.data}")
                    }
                    is Resource.Error -> {
                        Log.d("Data Konsumsi error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data Konsumsi", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Data Konsumsi exception", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    fun getKonsumsiLainnya(headers: Map<String, String>, tanggal: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response =
                    pantauKaloriRepository.getAllKonsumsiMakanan("LAINNYA", tanggal, headers)) {
                    is Resource.Success -> {
                        allKonsumsiLainnya = response.data!!
                        Log.d("Data Konsumsi", "${response.data}")
                    }
                    is Resource.Error -> {
                        Log.d("Data Konsumsi error", "${response.message}")
                    }
                    else -> {
                        Log.d("Data Konsumsi", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Data Konsumsi exception", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    fun tambahKonsumsiMakanan(
        waktuMakan: String,
        tanggal: String,
        data: DataKonsumsiMakananModel,
        headers: Map<String, String>
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = pantauKaloriRepository.tambahKonsumsiMakanan(data, headers)) {
                    is Resource.Success -> {
                        Log.d("tambahKonsumsiMakanan", "${response.data}")

                        getSarapan(headers, tanggal)
                        getMakanSiang(headers, tanggal)
                        getMakanMalam(headers, tanggal)
                        getKonsumsiLainnya(headers, tanggal)
                    }
                    is Resource.Error -> {
                        Log.d("Error tambahKonsumsiMakanan", "${response.data}")
                    }
                    else -> {
                        Log.d("Data", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error tambahKonsumsiMakanan Catch", "$e")
            } finally {
                isLoading = false
            }
        }
    }


    fun addKaloriKonsumiToDiary(totalKalori: String, context: Context) {
        viewModelScope.launch {
            val localStorage = CustomDataStore(context)
            CoroutineScope(Dispatchers.IO).launch {
                localStorage.saveTotalKonsumsiKalori("$totalKalori Cal")
            }
            Toast.makeText(
                context, "Berhasil menambahkan data",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}