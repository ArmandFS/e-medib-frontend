package com.example.e_medib.features.home_feature.view_model

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_medib.data.Resource
import com.example.e_medib.features.home_feature.model.catatan.DataCatatanModel
import com.example.e_medib.features.home_feature.model.catatan.response.GetAllCatatanRespone
import com.example.e_medib.features.home_feature.model.diary.DataDiaryModel
import com.example.e_medib.features.home_feature.model.gulaDarah.DataGulaDarahModel
import com.example.e_medib.features.home_feature.model.gulaDarah.getAll.GetAllGulaDarahResponse
import com.example.e_medib.features.home_feature.model.gulaDarah.getAll.GulaDarahData
import com.example.e_medib.features.home_feature.model.hba1c.DataHba1cModel
import com.example.e_medib.features.home_feature.model.hba1c.getAll.GetAllHba1cResponse
import com.example.e_medib.features.home_feature.model.hba1c.getAll.Hba1cData
import com.example.e_medib.features.home_feature.model.kolesterol.DataKolesterolModel
import com.example.e_medib.features.home_feature.model.kolesterol.getAll.GetAllKolesterolResponse
import com.example.e_medib.features.home_feature.model.kolesterol.getAll.KolesterolData
import com.example.e_medib.features.home_feature.model.tekananDarah.DataTekananDarahModel
import com.example.e_medib.features.home_feature.model.tekananDarah.getAll.TekananDarahData
import com.example.e_medib.features.home_feature.model.tekananDarah.getAll.GetAllTekananDarahResponse
import com.example.e_medib.features.home_feature.model.userData.DataUserModelResponse
import com.example.e_medib.features.home_feature.repository.HomeRepository
import com.example.e_medib.utils.CustomDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)

    var pilihHari by mutableStateOf("")

    var userData: DataUserModelResponse? by mutableStateOf(
        DataUserModelResponse(data = null, meta = null)
    )
    var allHba1cData: GetAllHba1cResponse by mutableStateOf(GetAllHba1cResponse(data = listOf()))
    var allGulaDarahData: GetAllGulaDarahResponse by mutableStateOf(GetAllGulaDarahResponse(data = listOf()))
    var allKolesterolData: GetAllKolesterolResponse by mutableStateOf(GetAllKolesterolResponse(data = listOf()))
    var allTekananDarahData: GetAllTekananDarahResponse by mutableStateOf(
        GetAllTekananDarahResponse(data = listOf())
    )
    var recentTekananDarah: TekananDarahData by mutableStateOf(TekananDarahData())
    var recentGulaDarah: GulaDarahData by mutableStateOf(GulaDarahData())
    var recentKolesterol: KolesterolData by mutableStateOf(KolesterolData())
    var recentHba1c: Hba1cData by mutableStateOf(Hba1cData())

    var todayTekananDarah: TekananDarahData by mutableStateOf(TekananDarahData())
    var todayGulaDarah: GulaDarahData by mutableStateOf(GulaDarahData())
    var todayKolesterol: KolesterolData by mutableStateOf(KolesterolData())

    var allCatatanData: GetAllCatatanRespone by mutableStateOf(
        GetAllCatatanRespone(data = listOf())
    )

    fun setHari(hari: String) {
        pilihHari = hari
    }


    fun getDataUser(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.getDataUser(headers)) {
                    is Resource.Success -> {
                        Log.d("Data User", "${response.data}")
                        userData = response.data!!
                    }
                    is Resource.Error -> {
                        Log.d("Data User", "${response.message}")
                    }
                    else -> {
                        Log.d("Data User", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Data User", "$e")

            } finally {
                isLoading = false
            }
        }
    }

    // HBA1C
    fun getAllHba1c(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.getAllHba1c(pilihHari, headers)) {
                    is Resource.Success -> {
                        allHba1cData = response.data!!
                        recentHba1c = response.data.data.first()
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

    fun hitungHba1c(
        data: DataHba1cModel, headers: Map<String, String>
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.hitungHba1c(data, headers)) {
                    is Resource.Success -> {
                        getAllHba1c(headers)
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

    // GULA DARAH
    fun getAllGulaDarah(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.getAllGulaDarah(pilihHari, headers)) {
                    is Resource.Success -> {
                        allGulaDarahData = response.data!!
                        recentGulaDarah = response.data.data.first()
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

    fun getTodayGulaDarah(headers: Map<String, String>, context: Context) {
        viewModelScope.launch {
            val localStorage = CustomDataStore(context)
            isLoading = true
            try {
                when (val response =
                    homeRepository.getAllGulaDarah(LocalDate.now().toString(), headers)) {
                    is Resource.Success -> {
                        todayGulaDarah = response.data!!.data.first()
                        val gulaDarah = todayGulaDarah.gula_darah
                        val keterangan = todayGulaDarah.keterangan
                        val status = todayGulaDarah.status

                        CoroutineScope(Dispatchers.IO).launch {
                            localStorage.saveGulaDarah("Kadar: $gulaDarah mg/dL. Keterangan: $keterangan. Status: $status")
                        }

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

    fun hitungGulaDarah(
        data: DataGulaDarahModel, headers: Map<String, String>,
        context: Context
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.hitungGulaDarah(data, headers)) {
                    is Resource.Success -> {
                        getAllGulaDarah(headers)
                        getTodayGulaDarah(headers, context)
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


    // KOLESTEROL
    fun getAllKolesterol(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.getAllKolesterol(pilihHari, headers)) {
                    is Resource.Success -> {
                        allKolesterolData = response.data!!
                        recentKolesterol = response.data.data.first()
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

    fun getTodayKolesterol(headers: Map<String, String>, context: Context) {
        viewModelScope.launch {
            val localStorage = CustomDataStore(context)
            isLoading = true
            try {
                when (val response =
                    homeRepository.getAllKolesterol(LocalDate.now().toString(), headers)) {
                    is Resource.Success -> {
                        todayKolesterol = response.data!!.data.first()
                        val kolesterol = todayKolesterol.kolesterol
                        val status = todayKolesterol.status

                        CoroutineScope(Dispatchers.IO).launch {
                            localStorage.saveKolesterol("Kadar: $kolesterol mg/dL. Status: $status")
                        }
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

    fun hitungKolesterol(
        data: DataKolesterolModel, headers: Map<String, String>, context: Context
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.hitungKolestertol(data, headers)) {
                    is Resource.Success -> {
                        getAllKolesterol(headers)
                        getTodayKolesterol(headers, context)
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

    // TEKANAN DARAH
    fun getAllTekananDarah(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.getAllTekananDarah(pilihHari, headers)) {
                    is Resource.Success -> {
                        allTekananDarahData = response.data!!
                        recentTekananDarah = response.data.data.first()
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

    fun getTodayTekananDarah(headers: Map<String, String>) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response =
                    homeRepository.getAllTekananDarah(LocalDate.now().toString(), headers)) {
                    is Resource.Success -> {
                        todayTekananDarah = response.data!!.data.first()
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


    fun hitungTekananDarah(
        data: DataTekananDarahModel, headers: Map<String, String>
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.hitungTekananDarah(data, headers)) {
                    is Resource.Success -> {
                        Log.d("Tekanan darah", "${response.data?.data}")
                        getAllTekananDarah(headers)
                        getTodayTekananDarah(headers)
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

    // CATATAN
    fun getAllCatatan(headers: Map<String, String>, context: Context) {
        viewModelScope.launch {
            val localStorage = CustomDataStore(context)
            isLoading = true
            try {
                when (val response = homeRepository.getAllCatatan(headers, pilihHari)) {
                    is Resource.Success -> {
                        allCatatanData = response.data!!
                        val recentCatatanData = response.data.data.first()
                        val gambarLuka = recentCatatanData.gambar_luka
                        var catatanLuka = recentCatatanData.catatan_luka
                        var catatanLainnya = recentCatatanData.catatan

                        CoroutineScope(Dispatchers.IO).launch {
                            localStorage.saveGambarLuka(gambarLuka.toString())
                            localStorage.saveCatatanLuka(catatanLuka)
                            localStorage.saveCatatanLainnya(catatanLainnya)
                        }
                    }
                    is Resource.Error -> {
                        Log.d("allCatatanData", "${response.message}")
                    }
                    else -> {
                        Log.d("allCatatanData", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("allCatatanData Catch", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    fun tambahCatatan(
        data: DataCatatanModel,
        gambarLukaFile: Bitmap,
        headers: Map<String, String>,
        context: Context
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = homeRepository.tambahCatatan(data, gambarLukaFile, headers)) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil membuat catatan", Toast.LENGTH_SHORT
                        ).show()
                        getAllCatatan(headers, context)
                    }
                    is Resource.Error -> {
                        Log.d("Error tambahCatatan", "${response.message}")
                    }
                    else -> {
                        Log.d("Data tambahCatatan", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error Catch tambahCatatan", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    // DIARY REKAP
    fun tambahDiaryRekap(
        data: DataDiaryModel,
        headers: Map<String, String>,
        context: Context,
    ) {
        viewModelScope.launch {
            val localStorage = CustomDataStore(context)
            isLoading = true
            try {
                when (val response = homeRepository.tambahDiaryRekap(data, headers)) {
                    is Resource.Success -> {
                        Toast.makeText(
                            context, "Berhasil membuat catatan Laporan", Toast.LENGTH_SHORT
                        ).show()
                        CoroutineScope(Dispatchers.IO).launch {
                            localStorage.deleteTempDataDiary()
                        }
                    }
                    is Resource.Error -> {
                        Log.d("Error tambahDiaryRekap", "${response.message}")
                    }
                    else -> {
                        Log.d("Data tambahDiaryRekap", "$response")
                    }
                }
            } catch (e: Exception) {
                Log.d("Error Catch tambahDiaryRekap", "$e")
            } finally {
                isLoading = false
            }
        }
    }


}