package com.example.e_medib.features.dsmq_feature.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HorizontalRadioButton(
    options: List<String>,
    textAboveOptions: List<String>,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
    val customSelectedColor = Color(0xFF5799FC)
    val unselectedColor = Color.Black

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEachIndexed { index, option ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .selectable(
                        selected = (index == selectedOption),
                        onClick = { onOptionSelected(index) }
                    )
                    .padding(8.dp)
                    .weight(1f),
            ) {
                Text(
                    text = textAboveOptions[index],
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .height(68.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                RadioButton(
                    selected = (index == selectedOption),
                    onClick = { onOptionSelected(index) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = customSelectedColor,
                        unselectedColor = unselectedColor
                    )
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(top = 5.dp),
                    fontSize = 13.sp
                )
            }
        }
    }
}






//@Composable
//fun HorizontalRadioButton(
//    options: List<String>,
//    textAboveOptions: List<String>,
//    selectedOption: Int?,
//    onOptionSelected: (Int) -> Unit
//) {
//    val customSelectedColor = Color(0xFF5799FC)
//    val unselectedColor = Color.Black
//
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        options.zip(textAboveOptions).forEachIndexed { index, (option, textAbove) ->
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .selectable(
//                        selected = (index == selectedOption),
//                        onClick = {
//                            Log.d("RadioButton", "Option $index selected")
//                            onOptionSelected(index)
//                        }
//                    )
//                    .padding(8.dp)
//                    .weight(1f),
//            ) {
//                Text(
//                    text = textAbove,
//                    modifier = Modifier
//                        .padding(bottom = 8.dp)
//                        .fillMaxWidth()
//                        .height(68.dp),
//                    fontSize = 12.sp,
//                    textAlign = TextAlign.Center
//                )
//                RadioButton(
//                    selected = (index == selectedOption),
//                    onClick = {
//                        Log.d("RadioButton", "RadioButton $index clicked")
//                        onOptionSelected(index)
//                    },
//                    colors = RadioButtonDefaults.colors(
//                        selectedColor = customSelectedColor,
//                        unselectedColor = unselectedColor
//                    )
//                )
//                Text(
//                    text = option,
//                    modifier = Modifier.padding(top = 5.dp),
//                    fontSize = 13.sp
//                )
//            }
//        }
//    }
//}





