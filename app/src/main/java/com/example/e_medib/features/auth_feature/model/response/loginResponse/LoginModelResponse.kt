package com.example.e_medib.features.auth_feature.model.response.loginResponse

data class LoginModelResponse(
    val access_token: AccessToken,
    val data: LoginResponse,
    val meta: Meta
)