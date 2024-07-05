package com.example.e_medib.features.home_feature.view

import CustomBottomSheet
import CustomExpandedCard
import CustomInputField
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_medib.features.home_feature.model.gulaDarah.DataGulaDarahModel
import com.example.e_medib.features.home_feature.model.hba1c.DataHba1cModel
import com.example.e_medib.features.home_feature.model.kolesterol.DataKolesterolModel
import com.example.e_medib.features.home_feature.model.tekananDarah.DataTekananDarahModel
import com.example.e_medib.features.home_feature.view_model.HomeViewModel
import com.example.e_medib.ui.theme.*
import com.example.e_medib.utils.CustomDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun KondisiKesehatanScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    val headerMap = mutableMapOf<String, String>()
    headerMap["Accept"] = "application/json"
    headerMap["Authorization"] = "Bearer ${tokenText.value}"

    // sheet state
    val sheetTekananDarah = com.dokar.sheets.rememberBottomSheetState()
    val sheetKolesterol = com.dokar.sheets.rememberBottomSheetState()
    val sheetGulaDarah = com.dokar.sheets.rememberBottomSheetState()
    val sheetHba1c = com.dokar.sheets.rememberBottomSheetState()

    // textfield controller
    val sistolik = rememberSaveable() { mutableStateOf("") }
    val diastolik = rememberSaveable() { mutableStateOf("") }
    val kolesterol = rememberSaveable() { mutableStateOf("") }
    val gulaDarah = rememberSaveable() { mutableStateOf("") }
    val keterangan = rememberSaveable() { mutableStateOf("") }
    val hba1c = rememberSaveable() { mutableStateOf("") }

    val jenisKeterangn = listOf("BERPUASA", "SEBELUM_MAKAN", "SESUDAH_MAKAN", "LAINNYA")
    val mExpanded = remember { mutableStateOf(false) }
    val mExpandKeterangan = remember { mutableStateOf(false) }
    val mTextFieldSize = remember { mutableStateOf(Size.Zero) }
    val icon = if (mExpanded.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    if (homeViewModel.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = mLightBlue)
        }
    } else
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // GULA DARAH
            CustomExpandedCard(
                header = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(6f)) {

                            Text(
                                text = "Gula Darah",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.SemiBold,
                                color = mGrayScale
                            )
                            Row(
                                modifier = Modifier.padding(top = 12.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = if (homeViewModel.allGulaDarahData.data.isNotEmpty()) "${homeViewModel.recentGulaDarah.gula_darah}" else " - ",
                                    style = MaterialTheme.typography.h4,
                                    fontWeight = FontWeight.Bold,
                                    color = mGrayScale
                                )
                                Text(
                                    text = "mg/dl",
                                    modifier = Modifier.padding(start = 12.dp),
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.SemiBold,
                                    color = mGrayScale
                                )
                            }


                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    sheetGulaDarah.expand()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = mLightBlue,
                                contentColor = mWhite,
                                disabledBackgroundColor = mLightGrayScale,
                                disabledContentColor = mBlack
                            ),
                            shape = RoundedCornerShape(32.dp),
                        ) {
                            Text(
                                text = "Tambah",
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.caption,
                                fontWeight = FontWeight.SemiBold,
                                color = mWhite
                            )
                        }
                    }
                },
                body = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        LazyColumn() {
                            items(homeViewModel.allGulaDarahData.data) {
                                val colorByStatus =
                                    if (it.status == "Normal") mGreen else if (it.status == "Sedang") mYellow else mLightBlue

                                Card(
                                    elevation = 2.dp,
                                    border = BorderStroke(1.dp, colorByStatus),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "Tanggal : ${it.created_at}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Kadar : ${it.gula_darah} mg/dl",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Status : ${it.status}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Keterangan : ${it.keterangan}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                    }
                                }

                            }
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            // HBA1C
            CustomExpandedCard(
                header = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(6f)) {
                            Text(
                                text = "Kadar Hba1c",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.SemiBold,
                                color = mGrayScale
                            )

                            Row(
                                modifier = Modifier.padding(top = 12.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = if (homeViewModel.allHba1cData.data.isNotEmpty()) "${homeViewModel.recentHba1c.kadar_hba1c}" else " - ",
                                    style = MaterialTheme.typography.h4,
                                    fontWeight = FontWeight.Bold,
                                    color = mGrayScale
                                )
                                Text(
                                    text = "%",
                                    modifier = Modifier.padding(start = 12.dp),
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.SemiBold,
                                    color = mGrayScale
                                )
                            }
                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    sheetHba1c.expand()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = mLightBlue,
                                contentColor = mWhite,
                                disabledBackgroundColor = mLightGrayScale,
                                disabledContentColor = mBlack
                            ),
                            shape = RoundedCornerShape(32.dp),
                        ) {
                            Text(
                                text = "Tambah",
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.caption,
                                fontWeight = FontWeight.SemiBold,
                                color = mWhite
                            )
                        }
                    }
                },
                body = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        LazyColumn() {
                            items(homeViewModel.allHba1cData.data) {
                                val colorByStatus =
                                    if (it.status == "Normal") mGreen else if (it.status == "Pre-diabetes") mYellow else mLightBlue


                                Card(
                                    elevation = 2.dp,
                                    border = BorderStroke(1.dp, colorByStatus),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "Tanggal : ${it.created_at}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Kadar : ${it.kadar_hba1c} %",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Status : ${it.status}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                    }
                                }

                            }
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TEKANAN DARAH
            CustomExpandedCard(
                header = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(6f)) {
                            Text(
                                text = "Tekanan Darah",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.SemiBold,
                                color = mGrayScale
                            )
                            Row(
                                modifier = Modifier.padding(top = 12.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = if (homeViewModel.allTekananDarahData.data.isNotEmpty()) "${homeViewModel.recentTekananDarah.sistolik.toString()} / ${homeViewModel.recentTekananDarah.diastolik.toString()}" else "-",
                                    style = MaterialTheme.typography.h4,
                                    fontWeight = FontWeight.Bold,
                                    color = mGrayScale
                                )
                                Text(
                                    text = "mmHg",
                                    modifier = Modifier.padding(start = 12.dp),
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.SemiBold,
                                    color = mGrayScale
                                )
                            }


                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    sheetTekananDarah.expand()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = mLightBlue,
                                contentColor = mWhite,
                                disabledBackgroundColor = mLightGrayScale,
                                disabledContentColor = mBlack
                            ),
                            shape = RoundedCornerShape(32.dp),
                        ) {
                            Text(
                                text = "Tambah",
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.caption,
                                fontWeight = FontWeight.SemiBold,
                                color = mWhite
                            )
                        }
                    }
                },
                body = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        LazyColumn() {
                            items(homeViewModel.allTekananDarahData.data) {
                                val colorByStatus =
                                    if (it.status == "Normal") mGreen else if (it.status == "Pre-Hipertensi") mYellow else mLightBlue

                                Card(
                                    elevation = 2.dp,
                                    border = BorderStroke(1.dp, colorByStatus),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(
                                                color = colorByStatus.copy(
                                                    alpha = 0.05f
                                                )
                                            )
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = "Tanggal : ${it.created_at}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Sistolik : ${it.sistolik} mmHg",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Diastolik : ${it.diastolik} mmHg",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Status : ${it.status}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                    }
                                }

                            }
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            // KOLESTEROL
            CustomExpandedCard(
                header = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(6f)) {
                            Text(
                                text = "Kolesterol",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.SemiBold,
                                color = mGrayScale
                            )
                            Row(
                                modifier = Modifier.padding(top = 12.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = if (homeViewModel.allKolesterolData.data.isNotEmpty()) "${homeViewModel.recentKolesterol.kolesterol}" else " - ",
                                    style = MaterialTheme.typography.h4,
                                    fontWeight = FontWeight.Bold,
                                    color = mGrayScale
                                )
                                Text(
                                    text = "mg/dl",
                                    modifier = Modifier.padding(start = 12.dp),
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.SemiBold,
                                    color = mGrayScale
                                )
                            }


                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    sheetKolesterol.expand()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = mLightBlue,
                                contentColor = mWhite,
                                disabledBackgroundColor = mLightGrayScale,
                                disabledContentColor = mBlack
                            ),
                            shape = RoundedCornerShape(32.dp),
                        ) {
                            Text(
                                text = "Tambah",
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.caption,
                                fontWeight = FontWeight.SemiBold,
                                color = mWhite
                            )
                        }
                    }
                },
                body = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        LazyColumn() {
                            items(homeViewModel.allKolesterolData.data) {
                                val colorByStatus =
                                    if (it.status == "Baik") mGreen else if (it.status == "Waspada") mYellow else mLightBlue

                                Card(
                                    elevation = 2.dp,
                                    border = BorderStroke(1.dp, colorByStatus),
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "Tanggal : ${it.created_at}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Kadar : ${it.kolesterol} mg/dl",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                        Text(
                                            text = "Status : ${it.status}",
                                            style = MaterialTheme.typography.body1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colorByStatus
                                        )
                                    }
                                }

                            }
                        }
                    }
                },
            )

        }

    // ======= BOTTOM SHEET =======
    // sheet tekanan darah
    CustomBottomSheet(
        state = sheetTekananDarah,
        isEnable = sistolik.value.isNotEmpty() && diastolik.value.isNotEmpty(),
        textFieldTitle = "Tekanan Darah",
        onClick = {
            val dataTekananDarah =
                DataTekananDarahModel(sistolik = sistolik.value, diastolik = diastolik.value)
            homeViewModel.hitungTekananDarah(dataTekananDarah, headerMap)
            sistolik.value = ""
            diastolik.value = ""
            scope.launch {
                sheetTekananDarah.collapse()
            }
        },
        body = {
            // TEXT FIELD
            Text(
                text = "Sistolik (mmHg)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = mGrayScale
            )
            CustomInputField(
                valueState = sistolik,
                placeholder = "Masukan sistolik",
                trailingIcon = null,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )

            Text(
                text = "Diastolik (mmHg)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = mGrayScale
            )
            CustomInputField(
                valueState = diastolik,
                placeholder = "Masukan diastolik",
                trailingIcon = null,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        })

    // sheet kolesterol
    CustomBottomSheet(
        state = sheetKolesterol,
        isEnable = kolesterol.value.isNotEmpty(),
        textFieldTitle = "Kolesterol",
        onClick = {
            val dataKolesterol = DataKolesterolModel(kolesterol.value)
            homeViewModel.hitungKolesterol(dataKolesterol, headerMap, context)
            kolesterol.value = ""
            scope.launch {
                sheetKolesterol.collapse()
            }
        },
        body = {
            // TEXT FIELD
            Text(
                text = "Kolesterol",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = mGrayScale
            )
            CustomInputField(
                valueState = kolesterol,
                placeholder = "Masukan kadar kolesterol",
                trailingIcon = null,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        })

    // sheet gula darah
    CustomBottomSheet(
        state = sheetGulaDarah,
        isEnable = gulaDarah.value.isNotEmpty() && keterangan.value.isNotEmpty(),
        textFieldTitle = "Gula Darah",
        onClick = {
            val dataGulaDarah = DataGulaDarahModel(gulaDarah.value, keterangan.value)
            homeViewModel.hitungGulaDarah(dataGulaDarah, headerMap, context)

            gulaDarah.value = ""
            keterangan.value = ""
            scope.launch {
                sheetGulaDarah.collapse()
            }
        },
        body = {
            // TEXT FIELD
            Text(
                text = "Gula Darah",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = mGrayScale
            )
            CustomInputField(
                valueState = gulaDarah,
                placeholder = "Masukan gula darah",
                trailingIcon = null,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )

            Text(
                text = "Keterangan",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = mGrayScale
            )
            OutlinedTextField(
                value = keterangan.value,
                onValueChange = { keterangan.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize.value = coordinates.size.toSize()
                    },
                placeholder = {
                    Text(
                        text = "Keterangan",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        color = mGrayScale
                    )
                },
                trailingIcon = {
                    Icon(
                        icon,
                        "contentDescription",
                        tint = mLightBlue,
                        modifier = Modifier.clickable {
                            mExpanded.value = !mExpanded.value
                        },
                    )
                },
                readOnly = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = mWhite,
                    textColor = mLightBlue,
                    unfocusedBorderColor = mLightGrayScale,
                    focusedBorderColor = mLightGrayScale
                ),
            )
            DropdownMenu(
                expanded = mExpanded.value,
                onDismissRequest = { mExpanded.value = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSize.value.width.toDp() })
            ) {
                jenisKeterangn.forEach { label ->
                    DropdownMenuItem(onClick = {
                        keterangan.value = label
                        mExpanded.value = false
                    }) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                color = mGrayScale
                            )
                            IconButton(onClick = {
                                keterangan.value = label
                                Log.d("label : ", keterangan.value)
                                mExpandKeterangan.value = !mExpandKeterangan.value
                            }) {
                                Icon(
                                    Icons.Outlined.Info,
                                    contentDescription = "contentDescription",
                                    tint = mGrayScale,

                                    )
                            }


                        }
                    }
                }
                KeteranganDropdownMenu(
                    label = keterangan.value,
                    mExpandKeterangan = mExpandKeterangan
                )
            }


        })

    // sheet hba1c
    CustomBottomSheet(
        state = sheetHba1c,
        isEnable = hba1c.value.isNotEmpty(),
        textFieldTitle = "Kadar Hba1c",
        onClick = {
            val dataHba1c = DataHba1cModel(kadar_hba1c = hba1c.value)
            homeViewModel.hitungHba1c(dataHba1c, headerMap)
            hba1c.value = ""
            scope.launch {
                sheetHba1c.collapse()
            }
        },
        body = {
            // TEXT FIELD
            Text(
                text = "Kadar Hba1c",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = mGrayScale
            )
            CustomInputField(
                valueState = hba1c,
                placeholder = "Masukan Kadar Hba1c",
                trailingIcon = null,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        })

}

@Composable
fun KeteranganDropdownMenu(label: String, mExpandKeterangan: MutableState<Boolean>) {
    DropdownMenu(
        expanded = mExpandKeterangan.value,
        onDismissRequest = { mExpandKeterangan.value = false },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            var judul = ""
            var deskripsi = ""

            if (label == "BERPUASA") {
                judul = "BERPUASA"
                deskripsi =
                    "Saat Berpuasa (Fasting): Pengukuran ini dilakukan setelah Anda berpuasa selama 8-12 jam. Biasanya dilakukan di pagi hari sebelum makan atau minum apa pun. Pengukuran ini memberikan gambaran tentang gula darah dasar Anda."
            }

            if (label == "SEBELUM_MAKAN") {
                judul = "SEBELUM MAKAN"
                deskripsi =
                    "Sebelum Makan (Preprandial): Pengukuran ini dilakukan sebelum Anda makan, biasanya diambil setelah periode puasa sekitar 8 jam. Pengukuran ini membantu menilai sejauh mana tubuh Anda dapat mengendalikan gula darah tanpa bantuan makanan."
            }

            if (label == "SESUDAH_MAKAN") {
                judul = "SESUDAH MAKAN"
                deskripsi =
                    "Sesudah Makan (Postprandial): Pengukuran ini diambil biasanya 2 jam setelah Anda makan. Pengukuran ini memberikan gambaran tentang bagaimana tubuh Anda menangani lonjakan gula darah yang dihasilkan dari makanan yang Anda konsumsi."
            }

            if (label == "LAINNYA") {
                judul = "LAINNYA"
                deskripsi = "Mengkonsumsi makanan seperti camilan"
            }

            Text(
                text = judul,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = mBlack,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = deskripsi,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                color = mBlack,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )

        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun KondisiKesehatanScreenPreview() {
    KondisiKesehatanScreen()

//    CustomExpandedCard(
//        headerLabel = "Tekanan Darah",
//        body = {}, onAddButton = {}, onListTab = {},
//    )
}