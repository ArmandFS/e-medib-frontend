package com.example.e_medib.features.aktivitas_feature.view

import CustomBottomSheet
import CustomInputField
import CustomLoadingOverlay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_medib.features.aktivitas_feature.model.DataUpdateAktivitasPenggunaModel
import com.example.e_medib.features.aktivitas_feature.view.component.CustomAktivitasCard
import com.example.e_medib.features.aktivitas_feature.view_model.AktivitasViewModel
import com.example.e_medib.features.home_feature.view_model.HomeViewModel
import com.example.e_medib.navigations.AppScreen
import com.example.e_medib.ui.theme.*
import com.example.e_medib.utils.CustomDataStore
import com.foreverrafs.datepicker.DatePickerTimeline
import com.foreverrafs.datepicker.state.rememberDatePickerState
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AktivitasPenggunaScreen(
    navController: NavController,
    aktivitasViewModel: AktivitasViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    tingkat_aktivitas: String?
) {
    val today = LocalDate.now()
    val datePickerState =
        rememberDatePickerState(initialDate = LocalDate.now())

    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    val scope = rememberCoroutineScope()
    // sheet state
    val sheetEditAktivitas = com.dokar.sheets.rememberBottomSheetState()
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // textfield controller
    val idAktivitas = rememberSaveable() { mutableStateOf(0) }
    val namaAktivitas = rememberSaveable() { mutableStateOf("") }
    val intensitasAktivitas = rememberSaveable() { mutableStateOf("") }
    val durasiAktivitas = rememberSaveable() { mutableStateOf("") }
    val beratBadanAktivitas = rememberSaveable() { mutableStateOf("") }

    LaunchedEffect(Unit, block = {
        aktivitasViewModel.setHari(LocalDate.now().toString())
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"
        tingkat_aktivitas?.let { aktivitasViewModel.getAllAktivitasPengguna(headerMap, it) }
    })


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Daftar Aktivitas $tingkat_aktivitas",
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
            // DATE ROW
            DatePickerTimeline(
                modifier = Modifier,
                state = datePickerState,
                selectedBackgroundColor = mLightBlue,
                selectedTextColor = mWhite,
                dateTextColor = mGrayScale,
                pastDaysCount = 7,
                onDateSelected = { selectedDate: LocalDate ->
                    aktivitasViewModel.setHari(selectedDate.toString())
                    val headerMap = mutableMapOf<String, String>()
                    headerMap["Accept"] = "application/json"
                    headerMap["Authorization"] = "Bearer ${tokenText.value}"
                    if (tingkat_aktivitas != null) {
                        aktivitasViewModel.getAllAktivitasPengguna(headerMap, tingkat_aktivitas)
                    }
                },
            )

            // AKTIVITAS
            if (aktivitasViewModel.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = mLightBlue)
                }
            } else
            // TAMBAH AKTIVITAS
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tambahkan Aktivitas",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold,
                        color = mBlack
                    )
                    IconButton(onClick = {
                        navController.navigate(AppScreen.DaftarAktivitasScreen.screen_route + "/$tingkat_aktivitas")
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add",
                            tint = mLightBlue
                        )
                    }
                }

            // COLUMN LAYOUT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if (aktivitasViewModel.dataAllAktivitasPengguna!!.data.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Tidak ada data",
                            modifier = Modifier.padding(3.dp),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Normal,
                            color = mBlack
                        )
                    }
                } else
                // LIST AKTITVITAS PENGGUNA
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        LazyColumn() {
                            aktivitasViewModel.dataAllAktivitasPengguna?.let { it1 ->
                                items(it1.data) {
                                    CustomAktivitasCard(
                                        idItem = it.id,
                                        nama = it.nama_aktivitas,
                                        tingkatAktivitas = it.tingkat_aktivitas,
                                        durasi = "${it.durasi}",
                                        kalori = it.kalori.toString(),
                                        rowIcon = {
                                            // Update Data
                                            IconButton(onClick = {
                                                scope.launch {
                                                    namaAktivitas.value = it.nama_aktivitas
                                                    intensitasAktivitas.value =
                                                        it.tingkat_aktivitas
                                                    idAktivitas.value = it.id
                                                    sheetEditAktivitas.expand()
                                                }
                                            }) {
                                                Icon(
                                                    modifier = Modifier.size(size = 20.dp),
                                                    imageVector = Icons.Outlined.Edit,
                                                    contentDescription = "Edit Icon",
                                                    tint = mLightBlue,
                                                )
                                            }

                                            // DELETE DATA
                                            IconButton(onClick = {
                                                idAktivitas.value = it.id
                                                intensitasAktivitas.value =
                                                    it.tingkat_aktivitas
                                                showDeleteDialog = !showDeleteDialog
                                            }) {
                                                Icon(
                                                    modifier = Modifier.size(size = 20.dp),
                                                    imageVector = Icons.Outlined.Delete,
                                                    contentDescription = "Delete Icon",
                                                    tint = mLightBlue,
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
            }

            // ROW BUTTON
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                // DATA
                // Jumlah menit
                Text(
                    text = "Jumlah Menit Aktivitas $tingkat_aktivitas: ${aktivitasViewModel.dataAllAktivitasPengguna?.total_menit} Menit",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 4.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    color = mBlack
                )

                // Jumlah kalori
                Text(
                    text = "Jumlah Kalori Aktivitas $tingkat_aktivitas : ${aktivitasViewModel.dataAllAktivitasPengguna?.total_kalori} Cal",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    color = mBlack
                )

            }

        }

        // ======= BOTTOM SHEET =======
        CustomBottomSheet(
            state = sheetEditAktivitas,
            isEnable = durasiAktivitas.value.isNotEmpty() && beratBadanAktivitas.value.isNotEmpty(),
            textFieldTitle = "Durasi Aktivitas",
            onClick = {
                val headerMap = mutableMapOf<String, String>()
                headerMap["Accept"] = "application/json"
                headerMap["Authorization"] = "Bearer ${tokenText.value}"

                val dataUpdateAktivitasPenggunaModel = DataUpdateAktivitasPenggunaModel(
                    durasiAktivitas.value,
                    beratBadanAktivitas.value
                )

                aktivitasViewModel.editAktivitasPengguna(
                    headerMap,
                    idAktivitas.value,
                    dataUpdateAktivitasPenggunaModel,
                    intensitasAktivitas.value,
                    context
                )

                scope.launch {
                    sheetEditAktivitas.collapse()
                }
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

    if (showDeleteDialog) {
        CustomDeleteDialog(onDismiss = { showDeleteDialog = !showDeleteDialog }, onDelete = {
            val headerMap = mutableMapOf<String, String>()
            headerMap["Accept"] = "application/json"
            headerMap["Authorization"] = "Bearer ${tokenText.value}"
            showDeleteDialog = !showDeleteDialog
            aktivitasViewModel.deleteAktivitasPengguna(
                headerMap,
                idAktivitas.value,
                intensitasAktivitas.value,
                context
            )

        })
    }

    // LOADING OVERLAY
    if (aktivitasViewModel.isLoadingDelete) {
        CustomLoadingOverlay()
    }
}

@Composable
fun CustomDeleteDialog(onDismiss: () -> Unit, onDelete: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hapus Aktivitas",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = mBlack
                    )
                }


                Text(
                    text = "Apakah anda yakin untuk menghapus aktivitas ini ?",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    color = mBlack
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // KEMBALI
                    TextButton(onClick = { onDismiss() }) {
                        Text(
                            text = "Kembali",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            color = mBlack
                        )
                    }

                    // HAPUS
                    TextButton(onClick = { onDelete() }) {
                        Text(
                            text = "Hapus",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            color = mLightBlue
                        )
                    }
                }
            }
        }
    }
}


