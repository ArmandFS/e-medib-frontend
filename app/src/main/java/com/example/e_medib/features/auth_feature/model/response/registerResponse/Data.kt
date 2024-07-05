package com.example.e_medib.features.auth_feature.model.response.registerResponse

data class Data(
    val created_at: String,
    val email: String,
    val id: Int,
    val nama_lengkap: String,
    val nik: String,
    val tanggal_lahir: String,
    val updated_at: String,
    val username: String
)