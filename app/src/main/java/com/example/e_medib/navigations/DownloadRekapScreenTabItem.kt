package com.example.e_medib.navigations

import androidx.compose.runtime.Composable
import com.example.e_medib.features.profile_feature.view.BMIScreen
import com.example.e_medib.features.profile_feature.view.download_rekap_tab_screen.Semua
import com.example.e_medib.features.profile_feature.view.download_rekap_tab_screen._14Hari
import com.example.e_medib.features.profile_feature.view.download_rekap_tab_screen._30Hari
import com.example.e_medib.features.profile_feature.view.download_rekap_tab_screen._7Hari

typealias DownloadRekapScreenTabItemComposableFun = @Composable () -> Unit

sealed class DownloadRekapScreenTabItem(
    val title: String, val screen: DownloadRekapScreenTabItemComposableFun
) {
    object Semua :
        DownloadRekapScreenTabItem(title = "Semua", screen = { Semua() })

    object _7Hari :
        DownloadRekapScreenTabItem(title = "7 Hari", screen = { _7Hari() })

    object _14Hari :
        DownloadRekapScreenTabItem(title = "14 Hari", screen = { _14Hari() })

    object _30Hari :
        DownloadRekapScreenTabItem(title = "30 Hari", screen = { _30Hari() })
}
