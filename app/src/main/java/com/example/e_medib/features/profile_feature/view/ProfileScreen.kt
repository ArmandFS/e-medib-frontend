package com.example.e_medib.features.profile_feature.view

import CustomLoadingOverlay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_medib.R
import com.example.e_medib.features.home_feature.view.CustomRowInfo
import com.example.e_medib.features.home_feature.view_model.HomeViewModel
import com.example.e_medib.features.profile_feature.view_model.ProfileViewModel
import com.example.e_medib.navigations.AppScreen
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mGrayScale
import com.example.e_medib.ui.theme.mLightBlue
import com.example.e_medib.ui.theme.mLightGrayScale
import com.example.e_medib.ui.theme.mWhite
import com.example.e_medib.utils.CustomDataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    LaunchedEffect(Unit, block = {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"
        homeViewModel.getDataUser(headerMap)
        profileViewModel.getAllBMI(headerMap)
        profileViewModel.getAllBMR(headerMap)
    })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profil",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = mWhite
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = mLightBlue)
            )
        }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 60.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // BOX PROFILE
            CustomBoxProfile(modifier = Modifier, profileViewModel, homeViewModel)

            // BUTTON EDIT
            OutlinedButton(
                onClick = {
                    navController.navigate(AppScreen.RegisterScreen.screen_route + "/${true}")
                },
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = mWhite,
                    contentColor = mLightBlue,
                    disabledBackgroundColor = mLightGrayScale,
                    disabledContentColor = mBlack
                ),
                border = BorderStroke(1.dp, mLightBlue),
                shape = RoundedCornerShape(32.dp),

                ) {
                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = mLightBlue
                )
            }
            // DETAIL AKUN
            Text(
                text = "Detail Akun",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomProfileListTile(
                title = "Kalkulator BMI/BMR",
                subtitle = "Ukur BMI dan BMRmu",
                leadingIcon = Icons.Outlined.Calculate,
                onClick = {
                    navController.navigate(AppScreen.HitungBMIScreen.screen_route)
                }
            )
            CustomProfileListTile(
                title = "Download Catatan Anda",
                subtitle = "Unduh Catatan Kesehatan anda",
                leadingIcon = Icons.Outlined.FileDownload,
                onClick = {
                    navController.navigate(AppScreen.DowwnloadRekapScreen.screen_route)
                }
            )


            // KEAMANAN AKUN
            Text(
                text = "Keamanan Akun",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 16.dp),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomProfileListTile(
                title = "Kebijakan Privasi",
                subtitle = "Kebijakan mengenai keamanan akunmu",
                leadingIcon = Icons.Outlined.Visibility,
                onClick = {}
            )
            CustomProfileListTile(
                title = "Disclaimer",
                subtitle = "Peringatan mengenai kesehatanmu",
                leadingIcon = Icons.Outlined.Info,
                onClick = {}
            )
            CustomProfileListTile(
                title = "Hubungi Kami",
                subtitle = "Layanan Customer Service siap membantu",
                leadingIcon = Icons.Outlined.HelpOutline,
                onClick = {}
            )
            CustomProfileListTile(
                title = "Log Out",
                subtitle = "Logout dari akun anda",
                leadingIcon = Icons.Outlined.Logout,
                onClick = {
                    showLogoutDialog = !showLogoutDialog
                }
            )

        }

    }

    if (showLogoutDialog) {
        LogoutDialog(onDismiss = { showLogoutDialog = !showLogoutDialog }, onLogout = {
            val headerMap = mutableMapOf<String, String>()
            headerMap["Accept"] = "application/json"
            headerMap["Authorization"] = "Bearer ${tokenText.value}"
            showLogoutDialog = !showLogoutDialog
            profileViewModel.doLogout(headerMap, context, navigate = {
                // CLEAR ALL SCREEN THEN GO TO SPLASHSCREEN
                navController.navigate(AppScreen.SplashScreen.screen_route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            })
        })
    }

    // LOADING OVERLAY
    if (profileViewModel.isLoading) {
        CustomLoadingOverlay()
    }
}

@Composable
fun CustomProfileListTile(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    leadingIcon: ImageVector,
    onClick: () -> Unit
) {
    Column(modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable { onClick() }) {
        Row(
            modifier = modifier.height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "Person",
                tint = mLightBlue
            )
            Column(
                modifier = modifier
                    .weight(4f)
                    .padding(start = 16.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = mBlack
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Normal,
                        color = mGrayScale
                    )
                }

            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "ChevronRight",
                tint = mLightBlue
            )
        }
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            color = mLightGrayScale,
            thickness = 2.dp
        )
    }

}

@Composable
fun CustomBoxProfile(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, color = mLightBlue),
        elevation = 0.dp
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // PROFILE NAME
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = "${homeViewModel.userData?.data?.nama_lengkap}",
                    modifier = modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = mBlack
                )

                Text(
                    text = "${homeViewModel.userData?.data?.username}",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    color = mGrayScale
                )
            }

            // STATUS KESEHATAN
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                CustomRowInfo(
                    modifier = Modifier,
                    titleRow1 = "BMI",
                    titleRow2 = "BMR",
                    titleRow3 = "",
                    infoRow1 = "${profileViewModel.recentBMIData.bmi}",
                    infoRow2 = "${profileViewModel.recentBMRData.bmr}",
                    infoRow3 = "",
                    unitRow1 = "Kg/M2",
                    unitRow2 = "Cal/day",
                    unitRow3 = "",
                )
                Divider(
                    modifier = Modifier.padding(top = 16.dp),
                    color = mLightGrayScale,
                    thickness = 1.dp
                )

                // ROW GULA DARAH
                CustomRowInfo(
                    modifier = Modifier,
                    titleRow1 = "Tinggi Badan",
                    infoRow1 = "${profileViewModel.recentBMIData.tinggi_badan}",
                    unitRow1 = "cm",
                    titleRow2 = "Berat Badan",
                    infoRow2 = "${profileViewModel.recentBMIData.berat_badan}",
                    unitRow2 = "kg",
                    titleRow3 = "",
                    infoRow3 = "",
                    unitRow3 = "",
                )

            }
        }

    }

}

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onLogout: () -> Unit) {
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
                        text = "Logout",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = mBlack
                    )
                }


                Text(
                    text = "Apakah anda yakin untuk keluar dari aplikasi ?",
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

                    // LOGOUT
                    TextButton(onClick = { onLogout() }) {
                        Text(
                            text = "Logout",
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


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}