package com.example.e_medib.features.home_feature.model.diary.response

data class DiaryResponse(
    val catatan: String,
    val catatan_luka: String,
    val created_at: String,
    val gambar_luka: String,
    val gula_darah: String,
    val id: Int,
    val kolesterol: String,
    val total_konsumsi_kalori: String,
    val total_pembakaran_kalori: String,
    val updated_at: String,
    val user_id: Int
)