package com.example.e_medib.features.dsmq_feature.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_medib.features.dsmq_feature.view_model.DsmqViewModel
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mLightBlue
import com.example.e_medib.ui.theme.mWhite
import com.example.e_medib.utils.CustomDataStore
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DsmqResultsScreen(
    navController: NavController,
    //userId: Int?,
    viewModel: DsmqViewModel = hiltViewModel()
) {


    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    LaunchedEffect(Unit, block = {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"
        viewModel.fetchResultsByUserId(headerMap )
    })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Hasil Asesmen Kesehatan DSMQ",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                                 IconButton(onClick = { navController.popBackStack() }) {
                                     Icon(
                                         imageVector = Icons.Filled.ArrowBack,
                                         contentDescription ="arrow_back",
                                         tint = mWhite)
                                 }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = mLightBlue)
            )
        }
    ) {it
        //inside scaffold
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
                 horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(62.dp))
            Text(
                text =  "Hasil DSMQ Anda",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            //score text
            Text(
                text = "${viewModel.dsmqResults?.data?.score }",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 60.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF32CD32)
                )

            //date text

            Text(text = "DSMQ Fill Date: ${viewModel.dsmqResults?.data?.fill_date}",
                 style = MaterialTheme.typography.headlineMedium,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                color = mLightBlue
                )

            //description
            CustomProfileCard(
                header = {
                    Text(
                        text = "Keterangan",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = mBlack
                        )
                },
                body = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start)
                    {
                        TextSection(
                            text = "Nilai Anda itu berdasarkan Diabetes Self-Management Questionnaire (DSMQ), yang menilai aktivitas perawatan diri terkait kontrol glikemik.",
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextSection(
                            text = "0 - 18: Membutuhkan Peningkatan\nAktivitas perawatan diri Anda memerlukan peningkatan yang signifikan untuk kontrol glikemik yang lebih baik.",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextSection(
                            text = "18 - 30: Average\nAktivitas perawatan diri Anda rata-rata, namun masih ada ruang untuk perbaikan.",
                            color = Color.Yellow
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextSection(
                            text = "30 - 48: Bagus\nAktivitas perawatan diri Anda baik dan mendukung pengendalian glikemik yang efektif.",
                            color = Color.Green
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextSection(
                            text = "Rekomendasi: Pertahankan kebiasaan Anda saat ini dan terus up-to-date tentang manajemen diabetes.",
                            color = Color.Blue
                        )
                       }
                    },
                )
           }

    }
}


@Composable
fun TextSection(text: String, color: Color){
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Justify,
        color = color
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomProfileCard(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    body: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color = mBlack),


    ) {
        Column(
            modifier = modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            header()
            body()
        }
    }
}