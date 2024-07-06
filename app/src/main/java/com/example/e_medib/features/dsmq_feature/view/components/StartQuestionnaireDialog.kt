package com.example.e_medib.features.dsmq_feature.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

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
            title = {
                Text(
                    text = "Mulai Kuesioner",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                        )
                    },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin mulai mengisi kuesioner?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                    )
                   },
            confirmButton = {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Button(
                        onClick = { onConfirm() },
                        colors = ButtonDefaults.buttonColors(containerColor = confirmButtonColor),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Iya", color = Color.White)
                    }
                }
            },
            dismissButton = {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = dismissButtonColor),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                        Text(text = "Tidak", color = Color.White)
                    }
                }
            }
        )
    }
}




