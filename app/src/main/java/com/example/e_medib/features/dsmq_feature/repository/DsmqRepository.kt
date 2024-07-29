package com.example.e_medib.features.dsmq_feature.repository


import com.example.e_medib.data.Resource
import com.example.e_medib.features.dsmq_feature.model.AnswerModel
import com.example.e_medib.features.dsmq_feature.model.answerresponse.AnswerResponse
import com.example.e_medib.features.dsmq_feature.model.questionresponse.QuestionResponse
import com.example.e_medib.features.dsmq_feature.model.resultsresponse.ResultsResponse
import com.example.e_medib.network.EMedibApi
import javax.inject.Inject


//the dsmq repository
class DsmqRepository @Inject constructor(private val api: EMedibApi) {


    suspend fun getQuestions(
        headers: Map<String, String>): Resource<QuestionResponse>{

        Resource.Loading(data = true)
        return try {
            val dataQuestion = api.getQuestions(headers)
            Resource.Success(data =  dataQuestion)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    //create function for submittingAnswers
    suspend fun submitAnswers(answerModel: AnswerModel, headers: Map<String, String>): Resource<AnswerResponse> {
        Resource.Loading(data = true)
        return try {
            val responseSubmit = api.submitAnswers(headers, answerModel)
            Resource.Success(data = responseSubmit)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }

    //create function to get results
    suspend fun getResultsByUserId(
        headers: Map<String, String>,
        ): Resource<ResultsResponse> {
        return try {
            val response = api.getResultsByUserId(headers)
            Resource.Success(data = response)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        } finally {
            Resource.Loading(data = false)
        }
    }
}
