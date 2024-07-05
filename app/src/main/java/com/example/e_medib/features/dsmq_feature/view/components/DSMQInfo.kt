package com.example.e_medib.features.dsmq_feature.view.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun DSMQInfo(
    onButtonClick: () -> Unit
) {
            Button(
                onClick = {onButtonClick()},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5799FC))
            ) {
                Text(text = "Apa itu Diabetes Self Management Questionnaire?")
            }

}