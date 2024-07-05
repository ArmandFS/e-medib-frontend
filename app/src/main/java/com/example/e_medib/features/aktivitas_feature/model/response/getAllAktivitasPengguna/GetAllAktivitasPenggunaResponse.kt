package com.example.e_medib.features.aktivitas_feature.model.response.getAllAktivitasPengguna

data class GetAllAktivitasPenggunaResponse(
    val `data`: List<AktivitasPenggunaResponse>,
    val total_kalori: String? = null,
    val total_menit: Int? = null
)