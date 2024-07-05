package com.example.e_medib.features.home_feature.model.catatan.response

data class CatatanResponse(
    val catatan: String,
    val catatan_luka: String,
    val created_at: String,
    val deleted_at: Any,
    val gambar_luka: Any? = null,
    val id: Int,
    val jenis_luka: String,
    val updated_at: String,
    val user_id: Int
)