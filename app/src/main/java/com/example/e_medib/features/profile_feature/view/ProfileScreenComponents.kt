package com.example.e_medib.features.profile_feature.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mLightBlue
import com.example.e_medib.ui.theme.mLightGrayScale

@OptIn(ExperimentalMaterialApi::class)
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
        border = BorderStroke(2.dp, color = mLightGrayScale),
        elevation = 0.dp,
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

@Composable
fun CustomProfileListTile(
    modifier: Modifier = Modifier,
    data: String,
    onClick: () -> Unit,
    onDownload: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, mLightGrayScale),
                RoundedCornerShape(50)
            )
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = data,

            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            color = mBlack
        )

        IconButton(onClick = onDownload) {
            Icon(
                imageVector = Icons.Outlined.FileDownload,
                contentDescription = "Download",
                tint = mLightBlue,
            )
        }

    }
}