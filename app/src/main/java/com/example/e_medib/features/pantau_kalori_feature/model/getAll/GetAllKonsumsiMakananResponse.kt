package com.example.e_medib.features.pantau_kalori_feature.model.getAll

data class GetAllKonsumsiMakananResponse(
    val data: List<KonsumsiMakananResponse>,
    val total_kalori_sarapan: String? = null,
    val total_kalori_makan_siang: String? = null,
    val total_kalori_makan_malam: String? = null,
    val total_kalori_lainnya: String? = null,
    val total_kalori: String? = null,
)