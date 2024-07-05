package com.example.e_medib.features.dsmq_feature.model

import com.example.e_medib.features.dsmq_feature.model.answerresponse.Answer


//revised AnswerModel data class
data class AnswerModel(
    val answers: List<Answer>,
    //val user_id: String,
    val fill_date: String
)
