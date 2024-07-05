package com.example.e_medib.network

import com.example.e_medib.features.aktivitas_feature.model.DataCreateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.model.DataUpdateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.model.response.getAllAktivitasPengguna.AktivitasPenggunaResponse
import com.example.e_medib.features.aktivitas_feature.model.response.getAllAktivitasPengguna.GetAllAktivitasPenggunaResponse
import com.example.e_medib.features.aktivitas_feature.model.response.getAllDaftarAktivitas.GetAllDaftarAktivitasResponse
import com.example.e_medib.features.auth_feature.model.DataRegisterModel
import com.example.e_medib.features.auth_feature.model.LoginModel
import com.example.e_medib.features.auth_feature.model.response.loginResponse.LoginModelResponse
import com.example.e_medib.features.auth_feature.model.response.registerResponse.RegisterResponse
import com.example.e_medib.features.dsmq_feature.model.AnswerModel
import com.example.e_medib.features.dsmq_feature.model.answerresponse.AnswerResponse
import com.example.e_medib.features.dsmq_feature.model.questionresponse.QuestionResponse
import com.example.e_medib.features.dsmq_feature.model.resultsresponse.ResultsResponse
import com.example.e_medib.features.home_feature.model.catatan.response.CatatanResponse
import com.example.e_medib.features.home_feature.model.catatan.response.GetAllCatatanRespone
import com.example.e_medib.features.home_feature.model.diary.DataDiaryModel
import com.example.e_medib.features.home_feature.model.diary.response.DiaryResponse
import com.example.e_medib.features.home_feature.model.diary.response.GetAllDiaryResponse
import com.example.e_medib.features.home_feature.model.gulaDarah.DataGulaDarahModel
import com.example.e_medib.features.home_feature.model.gulaDarah.getAll.GetAllGulaDarahResponse
import com.example.e_medib.features.home_feature.model.gulaDarah.hitung.HitungGulDarahResponse
import com.example.e_medib.features.home_feature.model.hba1c.DataHba1cModel
import com.example.e_medib.features.home_feature.model.hba1c.getAll.GetAllHba1cResponse
import com.example.e_medib.features.home_feature.model.hba1c.hitung.HitungHba1cResponse
import com.example.e_medib.features.home_feature.model.kolesterol.DataKolesterolModel
import com.example.e_medib.features.home_feature.model.kolesterol.getAll.GetAllKolesterolResponse
import com.example.e_medib.features.home_feature.model.kolesterol.hitung.HitungKolesterolResponse
import com.example.e_medib.features.home_feature.model.tekananDarah.DataTekananDarahModel
import com.example.e_medib.features.home_feature.model.tekananDarah.getAll.GetAllTekananDarahResponse
import com.example.e_medib.features.home_feature.model.tekananDarah.hitung.HitungTekananDarahResponse
import com.example.e_medib.features.home_feature.model.userData.DataUserModelResponse
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
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface EMedibApi {
    // =========================== AUTH =========================
    // LOGIN API
    @POST("login")
    suspend fun doLogin(@Body login: LoginModel?): LoginModelResponse


    // REGISTER API
    @POST("register")
    suspend fun doRegister(@Body data: DataRegisterModel): RegisterResponse


    // LOGOUT
    @GET("logout")
    suspend fun doLogout(@HeaderMap headers: Map<String, String>): LogoutModelResponse

    // UPDATE USER
    @PATCH("update-profile")
    suspend fun updateProfile(
        @HeaderMap headers: Map<String, String>,
        @Body data: DataRegisterModel,
    ): RegisterResponse

    // =========================== HOMESCREEN =========================
    // GET DATA USER
    @GET("account")
    suspend fun getUserData(@HeaderMap headers: Map<String, String>): DataUserModelResponse

    // TEKANAN DARAH
    @POST("hitung-tekanan-darah")
    suspend fun hitungTekananDarah(
        @Body data: DataTekananDarahModel?, @HeaderMap headers: Map<String, String>
    ): HitungTekananDarahResponse

    @GET("tekanan-darah")
    suspend fun getAllTekananDarah(
        @Query("tanggal") tanggal: String, @HeaderMap headers: Map<String, String>
    ): GetAllTekananDarahResponse

    // KOLESTEROL
    @POST("hitung-kolsterol")
    suspend fun hitungKolesterol(
        @Body data: DataKolesterolModel, @HeaderMap headers: Map<String, String>
    ): HitungKolesterolResponse

    @GET("kolesterol")
    suspend fun getAllKolesterol(
        @Query("tanggal") tanggal: String, @HeaderMap headers: Map<String, String>
    ): GetAllKolesterolResponse

    // GULA DARAH
    @POST("hitung-gula-darah")
    suspend fun hitungGulaDarah(
        @Body data: DataGulaDarahModel, @HeaderMap headers: Map<String, String>
    ): HitungGulDarahResponse

    @GET("gula-darah")
    suspend fun getAllGulaDarah(
        @Query("tanggal") tanggal: String, @HeaderMap headers: Map<String, String>
    ): GetAllGulaDarahResponse

    // HBA1C
    @POST("hitung-hba1c")
    suspend fun hitungHba1c(
        @Body data: DataHba1cModel, @HeaderMap headers: Map<String, String>
    ): HitungHba1cResponse

    @GET("hba1c")
    suspend fun getAllHba1c(
        @Query("tanggal") tanggal: String, @HeaderMap headers: Map<String, String>
    ): GetAllHba1cResponse

    // GET ALL CATATAM
    @GET("diaries")
    suspend fun getAllCatatan(
        @HeaderMap headers: Map<String, String>, @Query("tanggal") tanggal: String
    ): GetAllCatatanRespone

    // TAMBAH CATATAN
    @Multipart
    @POST("diaries")
    suspend fun tambahCatatan(
        @Part("jenis_luka") jenis_luka: String,
        @Part("catatan_luka") catatan_luka: String,
        @Part("catatan") catatan: String,
        @Part gambar_luka_file: MultipartBody.Part,
        @HeaderMap headers: Map<String, String>
    ): CatatanResponse

    // GET CATATAN BY ID
    @GET("diaries/{id}")
    suspend fun getCatatanById(
        @HeaderMap headers: Map<String, String>, @Path("id") id: Int,
    ): CatatanResponse

    // GET ALL DIARY LAPORAN
    @GET("rekap")
    suspend fun getAllDiaryRekap(
        @HeaderMap headers: Map<String, String>
    ): GetAllDiaryResponse

    // CREATE DIARY LAPORAN / REKAP
    @POST("tambah-rekap")
    suspend fun tambahDiaryRekap(
        @Body data: DataDiaryModel, @HeaderMap headers: Map<String, String>
    ): DiaryResponse


    // =========================== AKTIVITAS =========================
    // GET DAFTAR AKTIVITAS SEED
    @GET("aktivitas")
    suspend fun getAllDaftarAktivitas(
        @HeaderMap headers: Map<String, String>,
        @Query("tingkat_aktivitas") tingkatAktivitas: String
    ): GetAllDaftarAktivitasResponse

    // GET AKTIVITAS PENGGUNA
    @GET("aktivitas-user")
    suspend fun getAllAktivitasPengguna(
        @HeaderMap headers: Map<String, String>,
        @Query("tingkat_aktivitas") tingkatAktivitas: String = "",
        @Query("tanggal") tanggal: String,
    ): GetAllAktivitasPenggunaResponse

    // CREATE AKTIVITAS PENGGUNA
    @POST("aktivitas-user")
    suspend fun tabmbahAktivitasPengguna(
        @HeaderMap headers: Map<String, String>,
        @Body data: DataCreateAktivitasPenggunaModel,
    ): AktivitasPenggunaResponse

    // EDIT AKTIVITAS PENGGUNA
    @PATCH("aktivitas-user/{id}")
    suspend fun editAktivitasPengguna(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int,
        @Body data: DataUpdateAktivitasPenggunaModel,
    ): AktivitasPenggunaResponse

    // DELETE AKTIVITAS PENGGUNA
    @DELETE("aktivitas-user/{id}")
    suspend fun deleteAktivitasPengguna(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int,
    ): Any


    // =========================== PANTAU KALORI SCREEN =========================
    // GET ALL KONSUMSI MAKANAN
    @GET("konsumsi-makanan")
    suspend fun getAllKonsumsiMakanan(
        @Query("waktu_makan") waktuMakan: String,
        @Query("tanggal") tanggal: String,
        @HeaderMap headers: Map<String, String>
    ): GetAllKonsumsiMakananResponse


    // POST KONSUMSI MAKANAN
    @POST("tambah-konsumsi-makanan")
    suspend fun tambahKonsumsiMakanan(
        @Body data: DataKonsumsiMakananModel, @HeaderMap headers: Map<String, String>
    ): KonsumsiMakananResponse

    // =========================== PROFILE SCREEN =========================
    // POST BMI
    @POST("hitung-bmi")
    suspend fun hitungBMI(
        @Body data: DataBMIModel, @HeaderMap headers: Map<String, String>
    ): BMIResponse

    // GET ALL BMI API
    @GET("bmi")
    suspend fun getAllBMI(
        @HeaderMap headers: Map<String, String>
    ): GetAllBMIResponse

    // POST BMR
    @POST("hitung-bmr")
    suspend fun hitungBMR(
        @Body data: DataBMRModel, @HeaderMap headers: Map<String, String>
    ): BMRResponse

    // GET ALL BMI
    @GET("bmr")
    suspend fun getAllBMR(
        @HeaderMap headers: Map<String, String>
    ): GetAllBMRResponse

    // =========================== DSMQ SCREEN =========================
    //api and get data
    @GET("questions")
    suspend fun getQuestions(@HeaderMap headers: Map<String, String>): QuestionResponse

    //api and post data
    @POST("submit")
    suspend fun submitAnswers(@HeaderMap headers: Map<String , String>,
                              @Body request: AnswerModel): AnswerResponse

//    //api and get results
//    @GET("results/user/{userId}")
//    suspend fun getResultsByUserId(@HeaderMap headers: Map<String, String>,@Path("userId") userId: Int): ResultsResponse

    //api and get results
    @GET("results/user")
    suspend fun getResultsByUserId(@HeaderMap headers: Map<String, String>): ResultsResponse
}