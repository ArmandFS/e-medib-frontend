package com.example.e_medib.features.aktivitas_feature.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mLightGrayScale
import com.example.e_medib.ui.theme.mLightBlue


@Composable
fun CustomAktivitasCard(
    modifier: Modifier = Modifier,
    idItem: Int,
    nama: String,
    tingkatAktivitas: String,
    durasi: String,
    kalori: String,
    rowIcon: @Composable @UiComposable () -> Unit,
) {


    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = modifier.height(70.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier.weight(4f),
            ) {
                Text(
                    text = nama,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = mBlack
                )
                Text(
                    text = "Intensitas aktivitas : $tingkatAktivitas",
                    modifier = modifier.padding(bottom = 3.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = mBlack
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "$durasi Menit",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Normal,
                        color = mLightBlue
                    )
                    rowIcon()
                }

            }
            Text(
                text = "$kalori Cal",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = mLightBlue
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