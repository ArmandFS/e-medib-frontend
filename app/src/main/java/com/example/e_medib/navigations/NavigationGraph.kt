package com.example.e_medib.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.e_medib.features.aktivitas_feature.view.AktivitasPenggunaScreen
import com.example.e_medib.features.aktivitas_feature.view.DaftarAktivitasScreen
import com.example.e_medib.features.aktivitas_feature.view.PilihKategoryAktivitasScreen
import com.example.e_medib.features.auth_feature.view.LoginScreen
import com.example.e_medib.features.auth_feature.view.RegisterScreen
import com.example.e_medib.features.dsmq_feature.view.DsmqResultsScreen
import com.example.e_medib.features.dsmq_feature.view.DsmqScreen
import com.example.e_medib.features.home_feature.view.HomeScreen
import com.example.e_medib.features.pantau_kalori_feature.view.PantauKaloriScreen
import com.example.e_medib.features.profile_feature.view.DowwnloadRekapScreen
import com.example.e_medib.features.profile_feature.view.HitungBMIdanBMRScreen
import com.example.e_medib.features.profile_feature.view.ProfileScreen
import com.example.e_medib.features.splash_screen_feature.view.SplashScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreen.SplashScreen.screen_route) {
        // DEFINE ALL POSSIBLE SCREEN THAT APP WILL HAVE
        composable(AppScreen.SplashScreen.screen_route) {
            SplashScreen(navController = navController)
        }
        composable(AppScreen.Beranda.screen_route) {
            HomeScreen(navController = navController)
        }
        composable(AppScreen.LoginScreen.screen_route) {
            LoginScreen(navController = navController)
        }
        //composable buat screen dsmq
        composable(AppScreen.Dsmq.screen_route) {
            //daftar screen yg kita mau
            DsmqScreen(navController = navController)
            //DsmqResultsScreen(navController = navController)
        }

        val registerScreenRoute = AppScreen.RegisterScreen.screen_route
        composable(
            "$registerScreenRoute/{isEditProfile}", arguments = listOf(
                navArgument(name = "isEditProfile", builder = {
                    type = NavType.BoolType
                })
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getBoolean("isEditProfile").let { isEditProfile ->
                if (isEditProfile != null) {
                    RegisterScreen(navController = navController, isEditProfile = isEditProfile)
                }
            }
        }

        composable(AppScreen.PilihKategoriAktivitasScreen.screen_route) {
            PilihKategoryAktivitasScreen(navController = navController)
        }

        val aktivitaPenggunaRoute = AppScreen.AktivitasPenggunaScreen.screen_route
        composable(
            "$aktivitaPenggunaRoute/{tingkat_aktivitas}", arguments = listOf(
                navArgument(name = "tingkat_aktivitas", builder = { type = NavType.StringType })
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("tingkat_aktivitas").let { tingkat_aktivitas ->
                AktivitasPenggunaScreen(
                    navController = navController,
                    tingkat_aktivitas = tingkat_aktivitas
                )
            }
        }

        val aktivitasRoute = AppScreen.DaftarAktivitasScreen.screen_route
        composable(
            "$aktivitasRoute/{tingkat_aktivitas}", arguments = listOf(
                navArgument(name = "tingkat_aktivitas", builder = { type = NavType.StringType })
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("tingkat_aktivitas").let { tingkat_aktivitas ->
                DaftarAktivitasScreen(
                    navController = navController,
                    tingkat_aktivitas = tingkat_aktivitas
                )
            }
        }

        composable(AppScreen.PantauKalori.screen_route) {
            PantauKaloriScreen(navController = navController)
        }

        composable(AppScreen.Profil.screen_route) {
            ProfileScreen(navController = navController)
        }

        composable(AppScreen.HitungBMIScreen.screen_route) {
            HitungBMIdanBMRScreen(navController = navController)
        }

        composable(AppScreen.DowwnloadRekapScreen.screen_route) {
            DowwnloadRekapScreen(navController = navController)
        }
        
        composable(AppScreen.DsmqResultsScreen.screen_route){
            DsmqResultsScreen(navController = navController)
        }


    }
}