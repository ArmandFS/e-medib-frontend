package com.example.e_medib.features.aktivitas_feature.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_medib.data.Resource
import com.example.e_medib.features.aktivitas_feature.model.DataCreateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.model.DataUpdateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.model.response.getAllAktivitasPengguna.GetAllAktivitasPenggunaResponse
import com.example.e_medib.features.aktivitas_feature.model.response.getAllDaftarAktivitas.GetAllDaftarAktivitasResponse
import com.example.e_medib.features.aktivitas_feature.repository.AktivitasRepository
import com.example.e_medib.utils.CustomDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AktivitasViewModel @Inject constructor(
    private val aktivitasRepository: AktivitasRepository
) : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)
    var isLoadingDelete: Boolean by mutableStateOf(false)
    var pilihHari by mutableStateOf("")

    var dataAllAktivitas: GetAllDaftarAktivitasResponse? by mutableStateOf(
        GetAllDaftarAktivitasResponse(data = emptyList())
    )
    var dataAllAktivitasPengguna: GetAllAktivitasPenggunaResponse? by mutableStateOf(
        GetAllAktivitasPenggunaResponse(data = emptyList())
    )

    var dataTotalKaloridanDurasi: GetAllAktivitasPenggunaResponse? by mutableStateOf(
        GetAllAktivitasPenggunaResponse(data = emptyList())
    )

    fun setHari(hari: String) {
        pilihHari = hari
    }

    fun getAllAktivitas(headers: Map<String, String>, tingkatAktivitas: String) {
        viewModelScope.launch {
            isLoading = true

            try {
                when (val response =
                    aktivitasRepository.getDaftarAktivitas(headers, tingkatAktivitas)) {
                    is Resource.Success -> {
                        dataAllAktivitas = response.data!!
                    }
                    is Resource.Error -> {
                        Log.d("dataAllAktivitas", "${response.message}")
                    }
                    else -> {
                        Log.d("dataAllAktivitas", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("dataAllAktivitas", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    // ============ AKTIVITAS PENGGUNA ===============
    // get data durasi dan kalori
    fun getDataAktivitasPengguna(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true

            try {
                when (val response = aktivitasRepository.getAllAktivitasPengguna(
                    headers = headers, tanggal = pilihHari
                )) {
                    is Resource.Success -> {
                        dataTotalKaloridanDurasi = response.data!!
                    }
                    is Resource.Error -> {
                        Log.d("dataAllAktivitasPengguna", "${response.message}")
                    }
                    else -> {
                        Log.d("dataAllAktivitasPengguna", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("dataAllAktivitasPengguna", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    // get All aktivitas Pengguna
    fun getAllAktivitasPengguna(headers: Map<String, String>, tingkatAktivitas: String) {
        viewModelScope.launch {
            isLoading = true

            try {
                when (val response = aktivitasRepository.getAllAktivitasPengguna(
                    headers, tingkatAktivitas, pilihHari
                )) {
                    is Resource.Success -> {
                        dataAllAktivitasPengguna = response.data!!
                    }
                    is Resource.Error -> {
                        Log.d("dataAllAktivitasPengguna", "${response.message}")
                    }
                    else -> {
                        Log.d("dataAllAktivitasPengguna", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("dataAllAktivitasPengguna", "$e")

            } finally {
                isLoading = false
            }
        }
    }


    // tambahl aktivitas Pengguna
    fun tambahAktivitasPengguna(
        headers: Map<String, String>,
        data: DataCreateAktivitasPenggunaModel,
        context: Context,
        navigate: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = aktivitasRepository.tabmbahAktivitasPengguna(
                    headers,
                    data,
                )) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil menambahkan data", Toast.LENGTH_SHORT
                        ).show()

                        delay(1000L)
                        navigate()
                    }
                    is Resource.Error -> {
                        Log.d("tabmbahAktivitasPengguna", "${response.message}")
                    }
                    else -> {
                        Log.d("tabmbahAktivitasPengguna", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("tabmbahAktivitasPengguna", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    // Update aktivitas Pengguna
    fun editAktivitasPengguna(
        headers: Map<String, String>,
        id: Int,
        data: DataUpdateAktivitasPenggunaModel,
        tingkatAktivitas: String,
        context: Context,
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = aktivitasRepository.editAktivitasPengguna(headers, id, data)) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil mengubah data", Toast.LENGTH_SHORT
                        ).show()
                        getAllAktivitasPengguna(headers, tingkatAktivitas)
                    }
                    is Resource.Error -> {
                        Log.d("tabmbahAktivitasPengguna", "${response.message}")
                    }
                    else -> {
                        Log.d("tabmbahAktivitasPengguna", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("tabmbahAktivitasPengguna", "$e")

            } finally {
                isLoading = false
            }
        }
    }


    // delete aktivitas Pengguna
    fun deleteAktivitasPengguna(
        headers: Map<String, String>,
        id: Int,
        tingkatAktivitas: String,
        context: Context,
    ) {
        viewModelScope.launch {
            isLoadingDelete = true
            try {
                when (val response = aktivitasRepository.deleteAktivitasPengguna(headers, id)) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil menghapus data", Toast.LENGTH_SHORT
                        ).show()
                        getAllAktivitasPengguna(headers, tingkatAktivitas)
                    }
                    is Resource.Error -> {
                        Log.d("deleteAktivitasPengguna", "${response.message}")
                    }
                    else -> {
                        Log.d("deleteAktivitasPengguna", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("deleteAktivitasPengguna", "$e")

            } finally {
                isLoadingDelete = false
            }
        }
    }

    fun addKaloriAktivitasToDiary(totalKalori: String, context: Context) {
        viewModelScope.launch {
            val localStorage = CustomDataStore(context)
            CoroutineScope(Dispatchers.IO).launch {
                localStorage.saveTotalKaloriAktivitas("$totalKalori Cal")
            }
            Toast.makeText(
                context, "Berhasil menambahkan data",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}