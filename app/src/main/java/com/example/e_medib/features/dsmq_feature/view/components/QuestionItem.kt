package com.example.e_medib.features.dsmq_feature.view.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.e_medib.features.dsmq_feature.model.answerresponse.Answer
import com.example.e_medib.features.dsmq_feature.model.questionresponse.Option



@Composable
fun QuestionItem(
    question: String,
    options: List<Option>,
    onAnswerSelected: (Answer) -> Unit
) {
    var selectedOptionId by remember { mutableStateOf(-1) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = question,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        val optionTexts = options.map { it.option_text ?: "No options provided" }
        val textAboveOptions = listOf(
            "Tidak berlaku bagi saya",
            "Sedikit berlaku bagi saya",
            "Berlaku bagi saya",
            "Sangat berlaku bagi saya",
            )
        HorizontalRadioButton(
            options = optionTexts,
            textAboveOptions = textAboveOptions,
            selectedOption = selectedOptionId,
            onOptionSelected = { optionId ->
                Log.d("QuestionItem", "Option $optionId selected for question $question")
                selectedOptionId = optionId
                if (options.isNotEmpty()) {
                    val selectedOption = options[optionId]
                    selectedOption?.let {
                        val answer = Answer(
                            question_id = it.question_id.toString(),
                            answer = optionId.toString()
                        )
                        Log.d("QuestionItem", "Answer created: $answer")
                        onAnswerSelected(answer)
                    }
                }
            }
        )
    }
}












