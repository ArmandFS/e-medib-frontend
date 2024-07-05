package com.example.e_medib.features.aktivitas_feature.view


import CustomExpandedCard
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_medib.features.aktivitas_feature.view_model.AktivitasViewModel
import com.example.e_medib.navigations.AppScreen
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mGrayScale
import com.example.e_medib.ui.theme.mGreen
import com.example.e_medib.ui.theme.mLightBlue
import com.example.e_medib.ui.theme.mLightGrayScale
import com.example.e_medib.ui.theme.mWhite
import com.example.e_medib.ui.theme.mYellow
import com.example.e_medib.utils.CustomDataStore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilihKategoryAktivitasScreen(
    navController: NavController,
    aktivitasViewModel: AktivitasViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    LaunchedEffect(Unit, block = {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"
        aktivitasViewModel.getDataAktivitasPengguna(headerMap)
    })
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Pilih Intensitas Aktivitas",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = mWhite
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = mLightBlue)
            )

        }
    ) {it

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 60.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CustomPilihAktivitasCard(
                modifier = Modifier,
                title = "Aktivitas Ringan",
                caption = "Aktivitas fisik ini hanya memerlukan sedikit tenaga dan biasanya tidak menyebabkan perubahan dalam pernapasan, saat melakukan aktivitas masih dapat berbicara dan bernyanyi",
                color = mGreen,
                onClick = {
                    navController.navigate(AppScreen.AktivitasPenggunaScreen.screen_route + "/RINGAN")
                },
            )
            CustomPilihAktivitasCard(
                modifier = Modifier.padding(top = 16.dp),
                title = "Aktivitas Sedang",
                caption = "Aktivitas fisik sedang adalah aktivitas fisik dengan kebutuhan kalori sebesar 3,5 - 7 Kcal/menit. Saat menjalankan aktivitas fisik sedang, tubuh akan sedikit berkeringat, ritme napas lebih cepat, dan denyut jantung sedikit lebih kuat",
                color = mYellow,
                onClick = {
                    navController.navigate(AppScreen.AktivitasPenggunaScreen.screen_route + "/SEDANG")
                },
            )
            CustomPilihAktivitasCard(
                modifier = Modifier.padding(top = 16.dp),
                title = "Aktivitas Berat",
                caption = "Aktivitas fisik dikategorikan berat apabila selama beraktivitas tubuh mengeluarkan banyak berkeringat, denyut jantung dan frekuensi nafas sangat meningkat sampai dengan kehabisan napas.Energi yang dikeluarkan saat melakukan aktivitas pada kategori ini > 7 Kcal/menit.",
                color = mLightBlue,
                onClick = {
                    navController.navigate(AppScreen.AktivitasPenggunaScreen.screen_route + "/BERAT")
                },
            )

            // TOTAL MENIT DAN KALORI AKTIVITAS
            CustomExpandedCard(
                modifier = Modifier.padding(top = 16.dp),
                isExpanded = false,
                header = {
                    Column() {
                        // TOTAL DURASI
                        Text(
                            text = "Total Durasi :",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = mBlack
                        )
                        Text(
                            text = "${aktivitasViewModel.dataTotalKaloridanDurasi?.total_menit} Menit",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold,
                            color = mLightBlue
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // TOTAL KALORI
                        Text(
                            text = "Total Kalori :",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = mBlack
                        )
                        Text(
                            text = "${aktivitasViewModel.dataTotalKaloridanDurasi?.total_kalori} Cal",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold,
                            color = mLightBlue
                        )

                        Button(
                            onClick = {
                                aktivitasViewModel.dataTotalKaloridanDurasi?.total_kalori?.let { it1 ->
                                    aktivitasViewModel.addKaloriAktivitasToDiary(
                                        it1,
                                        context
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = mLightBlue,
                                contentColor = mWhite,
                                disabledBackgroundColor = mLightGrayScale,
                                disabledContentColor = mBlack
                            ),
                            shape = RoundedCornerShape(32.dp),
                        ) {
                            Text(
                                text = "Tambah ke Diary",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.SemiBold,
                                color = mWhite
                            )
                        }
                    }
                },
                body = {},
            )

            Spacer(modifier = Modifier.height(24.dp))

        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomPilihAktivitasCard(
    modifier: Modifier = Modifier,
    title: String,
    caption: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color = mLightGrayScale),
        elevation = 0.dp,
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(100.dp))
                    .size(24.dp)
                    .background(color)
            )
            Column(
                modifier = Modifier
                    .weight(6f)
                    .padding(start = 16.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = mBlack
                )
                Text(
                    text = caption,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = mGrayScale
                )

            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "ChevronRight",
                tint = mLightBlue,
            )


        }

    }


}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PilihAktivitasScreenPreview() {
//    PilihAktivitasScreen()
}