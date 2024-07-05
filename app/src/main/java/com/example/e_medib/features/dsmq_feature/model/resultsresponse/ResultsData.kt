package com.example.e_medib.features.dsmq_feature.model.resultsresponse


data class ResultsData(
    val created_at: String,
    val fill_date: String,
    val id: Int,
    val score: Int,
    val updated_at: String,
    val user_id: Int
)