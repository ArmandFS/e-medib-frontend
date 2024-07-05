package com.example.e_medib.features.aktivitas_feature.model.response.getAllDaftarAktivitas

data class GetAllDaftarAktivitasResponse(
    val `data`: List<DaftarAktivitasResponse>,
    val total_kalori: Int? = null,
    val total_menit: Int? = null
)