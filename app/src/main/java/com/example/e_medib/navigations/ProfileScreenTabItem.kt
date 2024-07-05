package com.example.e_medib.navigations

import androidx.compose.runtime.Composable
import com.example.e_medib.features.profile_feature.view.BMIScreen
import com.example.e_medib.features.profile_feature.view.BMRScreen


typealias ProfileScreenTabItemComposableFun = @Composable () -> Unit

sealed class ProfileScreenTabItem(
    val title: String, val screen: ProfileScreenTabItemComposableFun
) {
    object BMIScreen :
        ProfileScreenTabItem(title = "BMI", screen = { BMIScreen() })

    object BMRScreen :
        ProfileScreenTabItem(title = "BMR", screen = { BMRScreen() })
}
