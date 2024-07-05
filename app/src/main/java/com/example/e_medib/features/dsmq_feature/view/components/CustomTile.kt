package com.example.e_medib.features.dsmq_feature.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DownloadDone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_medib.ui.theme.mLightBlue

@Composable
fun CustomTile(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    leadingIcon: ImageVector,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, Color.LightGray)
            )
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,     
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                 imageVector = leadingIcon,
                 contentDescription = "Icon",
                 tint = Color.Black,
                 modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(36.dp))
            Column {
                Text(text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    color = Color.Black
                    )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    color = Color.Black
                )
            }
        }
        IconButton(onClick = { onClick }) {
            Icon(
                imageVector = Icons.Outlined.DownloadDone,
                contentDescription = "Download",
                tint = mLightBlue)
            
        }
        


    }









}