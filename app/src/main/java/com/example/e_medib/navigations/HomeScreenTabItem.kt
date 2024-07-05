package com.example.e_medib.navigations

import androidx.compose.runtime.Composable
import com.example.e_medib.features.home_feature.view.DiaryScreen
import com.example.e_medib.features.home_feature.view.KondisiKesehatanScreen

typealias ComposableFun = @Composable () -> Unit

sealed class HomeScreenTabItem(
    val title: String, val screen: ComposableFun
) {
    object KondisiKesehatan :
        HomeScreenTabItem(title = "Kondisi Kesehatan", screen = { KondisiKesehatanScreen() })

    object Diary :
        HomeScreenTabItem(title = "Diary", screen = { DiaryScreen() })
}
