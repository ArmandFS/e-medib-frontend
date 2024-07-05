package com.example.e_medib.features.auth_feature.model

data class DataRegisterModel(
    val nama_lengkap: String,
    val username: String,
    val nik: String,
    val email: String,
    val jenis_kelamin: String,
    val usia: String,
    val tinggi_badan: String,
    val berat_badan: String,
    val password: String,
)
