package com.example.e_medib.features.dsmq_feature.model.questionresponse

data class QuestionResponseData(
    val created_at: Any,
    val id: Int,
    val options: List<Option>,
    val question_text: String,
    val updated_at: Any
)