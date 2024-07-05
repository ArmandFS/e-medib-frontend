package com.example.e_medib.features.pantau_kalori_feature.model

data class DataKonsumsiMakananModel(
    val makanan: String,
    val porsi: String,
    val kalori: String,
    val jenis_waktu_makan: String,
    val kadar_glukosa: String? = null,
    val kadar_karbohidrat: String? = null,
    val kadar_protein: String? = null,
    val kandungan_gizi_lainnya: String? = null,
)
