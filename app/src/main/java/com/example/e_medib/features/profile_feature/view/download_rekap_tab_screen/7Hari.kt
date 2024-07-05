package com.example.e_medib.features.profile_feature.view.download_rekap_tab_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_medib.features.profile_feature.view.CustomProfileListTile

@Composable
fun _7Hari() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn() {
            items(0) {
                CustomProfileListTile(data = "Feb 8 - 14", onClick = {}, onDownload = {})
            }
        }

    }
}