package com.example.e_medib.features.dsmq_feature.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_medib.features.dsmq_feature.model.answerresponse.Answer
import com.example.e_medib.features.dsmq_feature.view.components.CustomTile
import com.example.e_medib.features.dsmq_feature.view.components.EnterDate
import com.example.e_medib.features.dsmq_feature.view.components.InfoAlertDialog
import com.example.e_medib.features.dsmq_feature.view.components.QuestionItem
import com.example.e_medib.features.dsmq_feature.view.components.StartQuestionnaireDialog
import com.example.e_medib.features.dsmq_feature.view_model.DsmqViewModel
import com.example.e_medib.navigations.AppScreen
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.utils.CustomDataStore
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DsmqScreen(
    navController: NavController,
    viewModel: DsmqViewModel = hiltViewModel(),
) {
    val customSelectedColor = Color(0xFF5799FC)
    val lightGreen = Color(0xFF13ECA2)
    val redColor = Color(0xFFF20D3F)

    val selectedAnswers = remember { mutableStateListOf<Answer>() }
    var showDialog by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }
    var startQuestionnaire by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var showInfoDialog by remember { mutableStateOf(false) }
    var justSubmitted by remember { mutableStateOf(false)}

    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    LaunchedEffect(Unit, block = {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"
        viewModel.fetchQuestions(headerMap)
    })

    //notification after submitting
    LaunchedEffect(justSubmitted) {
        if (justSubmitted) {
            delay(5000)
            justSubmitted = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Diabetes Self-Management Questionnaire",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = customSelectedColor)
            )
        }
    ) {it
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(62.dp))
            Button(
                onClick = { showInfoDialog = true },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(customSelectedColor)
            ) {
                Text(text = "Apa itu Diabetes Self Management Questionnaire?", color = Color.White)
            }

            if (showInfoDialog) {
                InfoAlertDialog(
                    onDismiss = { showInfoDialog = false }
                )
            }

            EnterDate(selectedDate = selectedDate, onDateSelected = { selectedDate = it })
            Spacer(modifier = Modifier.height(2.dp))

            Button(
                onClick = {
                    if (selectedDate.isNotEmpty()) {
                        showDialog = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(customSelectedColor)
            ) {
                Text(text = "Mulai Isi Kuesioner")
            }
            Spacer(modifier = Modifier.height(20.dp))
            StartQuestionnaireDialog(
                showDialog = showDialog,
                onDismiss = {
                    showDialog = false
                },
                onConfirm = {
                    showDialog = false
                    startQuestionnaire = true
                },
                confirmButtonColor = lightGreen,
                dismissButtonColor = redColor
            )

            if (startQuestionnaire) {
                Column {
                    viewModel.questions?.data?.forEach { it ->
                        QuestionItem(
                            question = it.question_text,
                            options = it.options ?: emptyList(),
                            onAnswerSelected = { answer ->
                                Log.d("DSMQ", "Answer Selected: $answer")
                                selectedAnswers.removeIf { it.question_id == answer.question_id }
                                selectedAnswers.add(answer)
                            }
                        )
                    }
                    Button(
                        onClick = { showSubmitDialog = true },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(customSelectedColor)
                    ) {
                        Text(text = "Selesai Kuesioner", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(76.dp))
                }
            }
            if (showSubmitDialog) {
                AlertDialog(
                    onDismissRequest = { showSubmitDialog = false },
                    title = { Text(text = "Selesai Kuesioner") },
                    text = { Text(text = "Apakah anda yakin menyelesaikan kuesioner ini? Mohon mengisi semua entri terlebih dahulu jika belum.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                val headerMap = mutableMapOf<String, String>()
                                headerMap["Accept"] = "application/json"
                                headerMap["Authorization"] = "Bearer ${tokenText.value}"
                                showSubmitDialog = false
                                Log.d("selected Answer", selectedAnswers.toString())
                                viewModel.submitAnswers(
                                    answers = selectedAnswers.toList(),
                                    fillDate = selectedDate,
                                    headers = headerMap)
                                startQuestionnaire = false
                                selectedAnswers.clear()
                                justSubmitted = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = lightGreen)
                        ) {
                            Text(text = "Iya", color = Color.White)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showSubmitDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = redColor)
                        ) {
                            Text(text = "Tidak", color = Color.White)
                        }
                    }
                )
            }


            androidx.compose.material.Text(
                text = "Mohon mengisi Kuesioner DSMQ terlebih dahulu untuk melihat hasil DSMQ.",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = mBlack
            )

            Spacer(modifier = Modifier.height(26.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally) {
                if (justSubmitted){
                    Text(
                        text = "Anda telah mengisi kuesioner!.",
                        color = lightGreen,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                        )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                CustomTile(
                    title = "Hasil DSMQ",
                    subtitle ="Pantau Hasil DSMQ Anda" ,
                    leadingIcon = Icons.Outlined.Calculate,
                    onClick = {
                        //navigasi ke halaman results
                        navController.navigate(AppScreen.DsmqResultsScreen.screen_route)
                    }
                )
            }
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}



