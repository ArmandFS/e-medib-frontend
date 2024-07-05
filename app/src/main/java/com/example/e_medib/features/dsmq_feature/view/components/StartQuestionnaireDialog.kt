package com.example.e_medib.features.dsmq_feature.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun StartQuestionnaireDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonColor: Color,
    dismissButtonColor: Color
){

    if (showDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = {onDismiss() },
            title = { Text(text = "Mulai Questionnaire") },
            text = { Text(text = "Apakah Anda yakin ingin mulai mengisi kuesioner? Mohon Mengisi Tanggal agar bisa mulai.")},

            confirmButton = {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Button(
                        onClick = { onConfirm() },
                        colors = ButtonDefaults.buttonColors(containerColor = confirmButtonColor)
                    ) {
                        Text(text = "Iya", color = Color.White)
                    }
                }
            },
            dismissButton = {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = dismissButtonColor)
                        ) {
                        Text(text = "Tidak", color = Color.White)
                    }
                }
            }
        )
    }
}




