package com.example.e_medib.features.profile_feature.view

import CustomInputField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_medib.features.profile_feature.model.bmiModel.DataBMIModel
import com.example.e_medib.features.profile_feature.view_model.ProfileViewModel
import com.example.e_medib.ui.theme.*
import com.example.e_medib.utils.CustomDataStore

@Composable
fun BMIScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    val tinggiBadan = rememberSaveable() { mutableStateOf("") }
    val beratBadan = rememberSaveable() { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // TITLE
        Text(
            text = "Masukan Tinggi dan Berat Badan anda",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = mBlack
        )

        // ROW INPUT TINGGI BADAN DAN BERAT BADAN
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TB
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Tinggi Badan",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = mBlack
                )
                CustomInputField(
                    valueState = tinggiBadan,
                    placeholder = "00",
                    trailingIcon = {
                        Text(
                            text = "cm",
                            style = MaterialTheme.typography.caption,
                            fontWeight = FontWeight.Bold,
                            color = mGrayScale
                        )
                    },
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                )

            }
            Spacer(modifier = Modifier.width(16.dp))

            // BB
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Berat Badan",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = mBlack
                )
                CustomInputField(
                    valueState = beratBadan,
                    placeholder = "00",
                    trailingIcon = {
                        Text(
                            text = "kg",
                            style = MaterialTheme.typography.caption,
                            fontWeight = FontWeight.Bold,
                            color = mGrayScale
                        )
                    },
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                )

            }

        }

        // BUTTON HITUNG
        Button(
            onClick = {
                val headerMap = mutableMapOf<String, String>()
                headerMap["Accept"] = "application/json"
                headerMap["Authorization"] = "Bearer ${tokenText.value}"

                val bmiData =
                    DataBMIModel(tinggi_badan = tinggiBadan.value, berat_badan = beratBadan.value)
                profileViewModel.hitungBMI(bmiData, headerMap)

            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = mLightBlue,
                contentColor = mWhite,
                disabledBackgroundColor = mLightGrayScale,
                disabledContentColor = mBlack
            ),
            shape = RoundedCornerShape(32.dp),
            enabled = tinggiBadan.value.isNotEmpty() && beratBadan.value.isNotEmpty()
        ) {
            Text(
                text = "Hitung",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = mWhite
            )
        }

        // HASIL PERHITUNGAN (TAB BMI DAN BMR)
        Text(
            text = "Hasil Perhitungan",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 12.dp),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = mBlack
        )

        CustomProfileCard(
            header = {
                Text(
                    text = "BMI Anda",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = mBlack
                )
            },
            body = {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    val colorByStatus =
                        if (profileViewModel.recentBMIData.status == "Normal") mGreen else if (profileViewModel.recentBMIData.status == "Underweight") mBlue else if (profileViewModel.recentBMIData.status == "Overweight") mYellow else mLightBlue

                    Text(
                        text = "${profileViewModel.recentBMIData.bmi} kg/m2", // ini aslinya field bmi
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = colorByStatus
                    )
                    Text(
                        text = "Kamu memiliki BMI ${profileViewModel.recentBMIData.status.toString()}",
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .fillMaxWidth()
                            .background(color = colorByStatus)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = mWhite
                    )

                }
            },
        )

        Spacer(modifier = Modifier.height(14.dp))

        CustomProfileCard(
            header = {
                Text(
                    text = "Keterangan",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = mBlack
                )
            },
            body = {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text(
                        text = profileViewModel.recentBMIData.keterangan.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Justify,
                        color = mBlack
                    )

                }
            },
        )
        Spacer(modifier = Modifier.height(14.dp))

    }
}