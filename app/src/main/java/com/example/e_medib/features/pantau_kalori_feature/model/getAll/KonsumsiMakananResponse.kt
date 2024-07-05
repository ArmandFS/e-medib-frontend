package com.example.e_medib.features.pantau_kalori_feature.model.getAll

data class KonsumsiMakananResponse(
    val created_at: String,
    val id: Int,
    val jenis_waktu_makan: String,
    val kalori: String,
    val makanan: String,
    val porsi: String,
    val kadar_glukosa: String,
    val kadar_karbohidrat: String,
    val kadar_protein: String,
    val kandungan_gizi_lainnya: String,
    val updated_at: String,
    val user_id: Int
)