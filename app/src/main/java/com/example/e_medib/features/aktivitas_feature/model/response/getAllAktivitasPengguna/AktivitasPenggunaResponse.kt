package com.example.e_medib.features.aktivitas_feature.model.response.getAllAktivitasPengguna

data class AktivitasPenggunaResponse(
    val created_at: String,
    val deleted_at: Any,
    val durasi: String,
    val id: Int,
    val id_nama_aktivitas: Int,
    val kalori: Int,
    val met: String,
    val nama_aktivitas: String,
    val tingkat_aktivitas: String,
    val updated_at: String,
    val user_id: Int
)