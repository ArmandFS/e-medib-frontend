package com.example.e_medib.features.home_feature.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mGrayScale
import com.example.e_medib.ui.theme.mLightBlue
import com.example.e_medib.ui.theme.mWhite


@Composable
fun CustomRowInfo(
    modifier: Modifier = Modifier.fillMaxWidth(),
    titleRow1: String?,
    titleRow2: String?,
    titleRow3: String?,
    infoRow1: String?,
    infoRow2: String?,
    infoRow3: String?,
    unitRow1: String?,
    unitRow2: String?,
    unitRow3: String?,
    useRow3Button: Boolean = false,
    onRow3Click: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ROW 1
        Column() {
            Text(
                text = titleRow1.toString(),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mGrayScale
            )

            Row(modifier = modifier.padding(top = 4.dp), verticalAlignment = Alignment.Bottom) {
                Text(
                    text = infoRow1.toString(),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    color = mBlack
                )
                Text(
                    text = unitRow1.toString(),
                    modifier = modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = mGrayScale
                )
            }
        }

        // ROW 2
        Column() {
            Text(
                text = titleRow2.toString(),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mGrayScale
            )
            Row(modifier = modifier.padding(top = 4.dp), verticalAlignment = Alignment.Bottom) {
                Text(
                    text = infoRow2.toString(),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    color = mBlack
                )
                Text(
                    text = unitRow2.toString(),
                    modifier = modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = mGrayScale
                )
            }
        }


        // ROW 3
        if (useRow3Button) Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = mLightBlue)
                .clickable { onRow3Click() }
        ) {
            Text(
                text = titleRow3.toString(),
                modifier = modifier.padding(10.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mWhite
            )
        } else
            Column() {
                Text(
                    text = titleRow3.toString(),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = mGrayScale
                )
                Row(modifier = modifier.padding(top = 4.dp), verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = infoRow3.toString(),
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.SemiBold,
                        color = mBlack
                    )
                    Text(
                        text = unitRow3.toString(),
                        modifier = modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Normal,
                        color = mGrayScale
                    )
                }
            }

    }

}