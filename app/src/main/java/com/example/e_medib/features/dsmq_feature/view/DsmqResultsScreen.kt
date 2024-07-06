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
                text =  "DSMQ Results",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            //score text
            Text(
                text = "Score: ${viewModel.dsmqResults?.data?.score }",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF32CD32)
                )

            //date text

            Text(text = "DSMQ Fill Date: ${viewModel.dsmqResults?.data?.fill_date}",
                 style = MaterialTheme.typography.headlineMedium,
                fontSize = 16.sp,
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
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Nilai Anda itu berdasarkan Diabetes Self-Management Questionnaire (DSMQ), " +
                                    "yang menilai aktivitas perawatan diri terkait kontrol glikemik. " +
                                    "Nilai berjangkau dari 0 sampai dengan 48.\n\n" +
                                    "0 - 18: Membutuhkan Peningkatan\n Aktivitas perawatan diri Anda memerlukan peningkatan yang signifikan untuk kontrol glikemik yang lebih baik." +
                                    "\nRekomendasi: Berkonsultasi dengan penyedia layanan kesehatan Anda untuk mengembangkan rencana perawatan diri yang lebih baik.\n\n" +
                                    "18 - 30: Average\n Aktivitas perawatan diri Anda rata-rata, namun masih ada ruang untuk perbaikan." +
                                    "\nRekomendasi: Lanjutkan upaya Anda dan mintalah saran untuk perbaikan berkelanjutan. \n\n" +
                                    "30 - 48: Bagus \n Aktivitas perawatan diri Anda baik dan mendukung pengendalian glikemik yang efektif" +
                                    "\nRekomendasi: Pertahankan kebiasaan Anda saat ini dan terus up-to-date tentang manajemen diabetes.",

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Justify,
                            color = mBlack
                           )
                       }
                    },
                )
           }

    }
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
        border = BorderStroke(2.dp, color = mLightBlue),
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