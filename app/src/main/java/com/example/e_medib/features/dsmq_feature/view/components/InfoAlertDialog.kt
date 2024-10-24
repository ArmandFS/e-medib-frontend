package com.example.e_medib.features.dsmq_feature.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoAlertDialog(
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },

        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Informasi DSMQ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                                    append("Ini merupakan alat yang dirancang untuk menilai perilaku ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                                        append("manajemen diri")
                                    }
                        append("pada penderita dengan diabetes")
                                                },
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                Text(
                    text = buildAnnotatedString {
                        append("Kuesioner ini membantu dalam mengevaluasi seberapa efektif seseorang dengan diabetes mengelola kondisinya dalam berbagai aspek")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                            append(" perawatan diri")
                        }
                        append(", seperti pola makan, kepatuhan terhadap obat, olahraga, dan lain-lain.")
                    },
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                Text(
                    text = "Jika ingin mengetahui lebih lengkap, bisa membuka article dengan copy paste link ini: ",
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                Text(
                    text = "https://pubmed.ncbi.nlm.nih.gov/23937988/",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5799FC))
                ) {
                    Text(text = "Kembali")
                }
            }
        },
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 6.dp)
            .width(300.dp)
    )
}