package com.example.e_medib.features.profile_feature.view

import CustomInputField
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_medib.features.profile_feature.model.bmrModel.DataBMRModel
import com.example.e_medib.features.profile_feature.view_model.ProfileViewModel
import com.example.e_medib.ui.theme.*
import com.example.e_medib.utils.CustomDataStore

@Composable
fun BMRScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val jenisKelaminExpand = remember { mutableStateOf(false) }
    val mTextFieldSize = remember { mutableStateOf(Size.Zero) }
    val icon = if (jenisKelaminExpand.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val tinggiBadan = rememberSaveable() { mutableStateOf("") }
    val beratBadan = rememberSaveable() { mutableStateOf("") }
    val selectedJenisKelamin = rememberSaveable() { mutableStateOf("") }
    val usia = rememberSaveable() { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // TITLE
        Text(
            text = "Masukan data untuk perhitungan BMR anda",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = mBlack
        )

        // ROW TINGGI & BERAT
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
                    imeAction = ImeAction.Next,
                )

            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        // ROW JENIS KELAMIN & USIA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // JENIS KELAMIN
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Jenis Kelamin",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = mBlack
                )
                OutlinedTextField(
                    value = selectedJenisKelamin.value,
                    onValueChange = { selectedJenisKelamin.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            mTextFieldSize.value = coordinates.size.toSize()
                        },
                    placeholder = {
                        Text(
                            text = "Jenis Kelamin",
                            style = MaterialTheme.typography.body1,
//                            modifier = Modifier.padding(6.dp),
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            color = mGrayScale
                        )
                    },
                    trailingIcon = {
                        Icon(
                            icon,
                            "contentDescription",
                            tint = mGrayScale,
                            modifier = Modifier.clickable {
                                jenisKelaminExpand.value = !jenisKelaminExpand.value
                            },
                        )
                    },
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = mWhite,
                        textColor = mBlack,
                        unfocusedBorderColor = mLightGrayScale,
                        focusedBorderColor = mLightGrayScale
                    ),
                )
                DropdownMenu(
                    expanded = jenisKelaminExpand.value,
                    onDismissRequest = { jenisKelaminExpand.value = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { mTextFieldSize.value.width.toDp() })
                ) {
                    jenisKelamin.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedJenisKelamin.value = label
                            jenisKelaminExpand.value = false
                        }) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                color = mGrayScale
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            // USIA
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Usia",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = mBlack
                )
                CustomInputField(
                    valueState = usia,
                    placeholder = "22 Tahun",
                    trailingIcon = {
                        Text(
                            text = "tahun",
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

                val bmrData =
                    DataBMRModel(
                        tinggiBadan.value,
                        beratBadan.value,
                        usia.value,
                       if(selectedJenisKelamin.value == "Laki-laki") "L" else "P"
                    )
                profileViewModel.hitungBMR(bmrData, headerMap)
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
            enabled = tinggiBadan.value.isNotEmpty() && beratBadan.value.isNotEmpty()  && usia.value.isNotEmpty()  && selectedJenisKelamin.value.isNotEmpty()
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
                    text = "BMR Anda",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = mBlack
                )
            },
            body = {
                Text(
                    text = "${profileViewModel.recentBMRData.bmr.toString()} Cal/hari",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = mLightBlue
                )
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
                        text = "Anda membutuhkan setidaknya ${profileViewModel.recentBMRData.bmr.toString()} kalori perhari.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = mBlack
                    )
                    Text(
                        text = "Jumlah kalori tersebut merupakan jumlah kalori yang dibutuhkan tubuh untuk melakukan aktivitas dasar tubuh seperti bernapas, memompa jantung, mencerna makanan, memperbaiki sel tubuh, dan lain sebagainya.",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Justify,
                        color = mBlack
                    )
                }
            },
        )

    }
}