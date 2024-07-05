package com.example.e_medib.features.profile_feature.view.download_rekap_tab_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_medib.features.profile_feature.view.CustomProfileListTile
import com.example.e_medib.features.profile_feature.view_model.ProfileViewModel
import com.example.e_medib.utils.CustomDataStore

@Composable
fun Semua(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    LaunchedEffect(Unit, block = {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"

        profileViewModel.getAllDiaryRekap(headerMap)
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn() {
            items(profileViewModel.allDiaryRekapData.data) {
                CustomProfileListTile(
                    data = it.created_at.split("T")[0],
                    onClick = {},
                    onDownload = {})
            }
        }

    }
}