package com.example.e_medib.features.aktivitas_feature.view

import CustomBottomSheet
import CustomInputField
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_medib.features.aktivitas_feature.model.DataCreateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.view_model.AktivitasViewModel
import com.example.e_medib.ui.theme.*
import com.example.e_medib.utils.CustomDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DaftarAktivitasScreen(
    navController: NavController,
    aktivitasViewModel: AktivitasViewModel = hiltViewModel(),
    tingkat_aktivitas: String?
) {

    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    val scope = rememberCoroutineScope()
    // sheet state
    val sheetTambahAktivitas = com.dokar.sheets.rememberBottomSheetState()


    // textfield controller
    val idNamaAktivitas = rememberSaveable() { mutableStateOf("") }
    val namaAktivitas = rememberSaveable() { mutableStateOf("") }
    val met = rememberSaveable() { mutableStateOf("") }
    val intensitasAktivitas = rememberSaveable() { mutableStateOf("") }
    val durasiAktivitas = rememberSaveable() { mutableStateOf("") }
    val beratBadanAktivitas = rememberSaveable() { mutableStateOf("") }


    LaunchedEffect(Unit, block = {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"
        if (tingkat_aktivitas != null) {
            aktivitasViewModel.getAllAktivitas(headerMap, tingkat_aktivitas)
        }
        Log.d("tingkat_aktivitas", "$tingkat_aktivitas")
    })

    if (aktivitasViewModel.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = mLightBlue)
        }
    } else
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Pilih Aktivitas $tingkat_aktivitas",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = mWhite
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "arrow_back",
                                tint = mWhite
                            )
                        }
                    },
                    backgroundColor = mLightBlue
                )
            }
        ) {padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // COLUMN LAYOUT
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        // LIST AKTITVITAS
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            LazyColumn() {
                                aktivitasViewModel.dataAllAktivitas?.let { it1 ->
                                    items(it1.data) {
                                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                            Row(
                                                modifier = Modifier.height(70.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(
                                                    modifier = Modifier.weight(4f),
                                                ) {
                                                    Text(
                                                        text = it.nama_aktivitas,
                                                        style = MaterialTheme.typography.body1,
                                                        fontWeight = FontWeight.SemiBold,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        color = mBlack
                                                    )
                                                    Text(
                                                        text = "Intensitas aktivitas : ${it.tingkat_aktivitas}",
                                                        modifier = Modifier.padding(bottom = 3.dp),
                                                        style = MaterialTheme.typography.caption,
                                                        fontWeight = FontWeight.Normal,
                                                        color = mBlack
                                                    )
                                                }
                                                IconButton(onClick = {
                                                    scope.launch {
                                                        namaAktivitas.value = it.nama_aktivitas
                                                        idNamaAktivitas.value = it.id.toString()
                                                        met.value = it.met
                                                        intensitasAktivitas.value =
                                                            it.tingkat_aktivitas

                                                        sheetTambahAktivitas.expand()
                                                    }
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Add,
                                                        contentDescription = "Add Icon",
                                                        tint = mLightBlue,
                                                    )
                                                }
                                            }
                                            Divider(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 12.dp),
                                                color = mLightGrayScale,
                                                thickness = 2.dp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }


            }

            // ======= BOTTOM SHEET =======
            CustomBottomSheet(
                state = sheetTambahAktivitas,
                isEnable = durasiAktivitas.value.isNotEmpty() && beratBadanAktivitas.value.isNotEmpty(),
                textFieldTitle = "Durasi Aktivitas",
                onClick = {
                    val headerMap = mutableMapOf<String, String>()
                    headerMap["Accept"] = "application/json"
                    headerMap["Authorization"] = "Bearer ${tokenText.value}"

                    val dataCreateAktivitasPenggunaModel = DataCreateAktivitasPenggunaModel(
                        id_nama_aktivitas = idNamaAktivitas.value,
                        namaAktivitas.value,
                        met.value,
                        intensitasAktivitas.value,
                        beratBadanAktivitas.value,
                        durasiAktivitas.value
                    )

                    // Tambah akticitas
                    aktivitasViewModel.tambahAktivitasPengguna(
                        headerMap,
                        dataCreateAktivitasPenggunaModel,
                        context = context, navigate = {
                            navController.popBackStack()
                        }
                    )
                },
                body = {
                    // NAMA AKTVITAS
                    Text(
                        text = "Nama Aktivitas",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp, top = 16.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = mGrayScale
                    )
                    CustomInputField(
                        valueState = namaAktivitas,
                        placeholder = "12 Menit",
                        trailingIcon = null,
                        readOnly = true,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )

                    // INTENSITAS
                    Text(
                        text = "Intensitas Aktivitas",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp, top = 16.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = mGrayScale
                    )
                    CustomInputField(
                        valueState = intensitasAktivitas,
                        placeholder = "12 Menit",
                        trailingIcon = null,
                        readOnly = true,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )

                    // DURASI
                    Text(
                        text = "Durasi (Menit)",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp, top = 16.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = mGrayScale
                    )
                    CustomInputField(
                        valueState = durasiAktivitas,
                        placeholder = "12 Menit",
                        trailingIcon = null,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )

                    // BERAT BADAN
                    Text(
                        text = "Berat badan (kg)",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp, top = 16.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = mGrayScale
                    )
                    CustomInputField(
                        valueState = beratBadanAktivitas,
                        placeholder = "Masukan berat badan",
                        trailingIcon = null,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    )
                }
            )
        }
}

