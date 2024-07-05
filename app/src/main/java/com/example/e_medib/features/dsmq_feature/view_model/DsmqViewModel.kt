package com.example.e_medib.features.dsmq_feature.view_model


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_medib.data.Resource
import com.example.e_medib.features.dsmq_feature.model.AnswerModel
import com.example.e_medib.features.dsmq_feature.model.answerresponse.Answer
import com.example.e_medib.features.dsmq_feature.model.questionresponse.QuestionResponse
import com.example.e_medib.features.dsmq_feature.model.resultsresponse.ResultsResponse
import com.example.e_medib.features.dsmq_feature.repository.DsmqRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class DsmqViewModel @Inject constructor(private val repository: DsmqRepository) : ViewModel() {
      //include a toast message
      private val _toastMessage = MutableStateFlow<String?>(null)
      val toastMessage = _toastMessage.asStateFlow()

      var isLoading: Boolean by mutableStateOf(false)
      var questions: QuestionResponse? by mutableStateOf(QuestionResponse(data = null))
      var dsmqResults: ResultsResponse? by mutableStateOf(null)
      var resultErrorMessage: String? by mutableStateOf(null)
      var dsmqLoading: Boolean by mutableStateOf(true)



        fun fetchQuestions(
        headers: Map<String, String>
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = repository.getQuestions(headers)) {
                    is Resource.Success -> {
                        Log.d("DSMQ DATA", "${response.data}")
                        questions = response.data!!
                    }
                    is Resource.Error -> {
                        Log.d("DSMQ DATA", "${response.message}")
                    }
                    else -> {
                        Log.d("DSMQ DATA", "$response")
                    }
                }
            } catch (e: HttpException) {
                Log.e("DsmqViewModel", "Failed to fetch questions: ${e.message}")
            } finally {
                isLoading  = false
            }
        }
    }
    fun submitAnswers(answers: List<Answer>, headers: Map<String, String> ,  fillDate: String) {
        viewModelScope.launch {
            isLoading = true
            val answerModel = AnswerModel(
                answers = answers,
                fill_date = fillDate
            )
            Log.d("DSMQ SUBMIT", answerModel.toString())
            try {
                when (val response = repository.submitAnswers(answerModel, headers)) {
                    is Resource.Success -> {
                        Log.d("DSMQ SUBMIT", "Answers submitted successfully: ${response.data}")
                    }
                    is Resource.Error -> {
                        Log.d("DSMQ SUBMIT", "Failed to submit answers: ${response.message}")
                    }
                    else -> {
                        Log.d("DSMQ SUBMIT", "Unknown response: $response")
                    }
                }
            } catch (e: HttpException) {
                Log.e("DsmqViewModel", "Failed to submit answers: ${e.response()}")
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchResultsByUserId(
        headers: Map<String, String>
        ) {
        viewModelScope.launch {
            dsmqLoading = true
            try {
                when (val response = repository.getResultsByUserId(headers)) {
                    is Resource.Success -> {
                        Log.d("DSMQ RESULTS", "Results fetched successfully: ${response.data}")
                        dsmqResults = response.data
                        resultErrorMessage = null
                    }
                    is Resource.Error -> {
                        Log.d("DSMQ RESULTS", "Failed to fetch results: ${response.message}")
                        resultErrorMessage = response.message
                    }
                    else -> {
                        Log.d("DSMQ RESULTS", "Unknown response: $response")
                        resultErrorMessage = "Unknown error occurred"
                    }
                }
            } catch (e: HttpException) {
                Log.e("DsmqViewModel", "Failed to fetch results: ${e.message}")
                resultErrorMessage = "Failed to fetch results: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }




}