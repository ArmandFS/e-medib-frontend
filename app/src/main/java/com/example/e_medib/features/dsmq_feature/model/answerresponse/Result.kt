package com.example.e_medib.features.dsmq_feature.model.answerresponse

data class Result(
    val answer_value: String,
    val created_at: String,
    val id: Int,
    val question_id: String,
    val updated_at: String,
    val user_id: String
)