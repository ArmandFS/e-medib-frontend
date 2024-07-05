package com.example.e_medib.features.splash_screen_feature.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_medib.R
import com.example.e_medib.navigations.AppScreen
import com.example.e_medib.ui.theme.mWhite
import com.example.e_medib.utils.CustomDataStore


@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    // DELAY FOR SEVERAL TIME THEN GO TO NEXT PAGE
    LaunchedEffect(key1 = true, block = {
        // delay(500L)
        Log.d("Token: ", tokenText.value)
        navController.navigate(AppScreen.Beranda.screen_route) {
                popUpTo(AppScreen.SplashScreen.screen_route) {
                    inclusive = true
                }
            }

        //code buat non aktifin login dan register
        if (tokenText.value.isEmpty()) {
            navController.navigate(AppScreen.LoginScreen.screen_route) {
                popUpTo(AppScreen.SplashScreen.screen_route) {
                    inclusive = true
                }
            }
        } else navController.navigate(AppScreen.Beranda.screen_route) {
            popUpTo(AppScreen.SplashScreen.screen_route) {
                inclusive = true
            }
        }

    })

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "app_logo", modifier = Modifier.size(100.dp)
            )
            Text(
                text = "E-Medib",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Bold,
                color = mWhite
            )
            Text(
                text = "Electronic Diary for Diabetes",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                color = mWhite
            )
        }
    }
}
