package com.example.e_medib.features.pantau_kalori_feature.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_medib.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchMenuScreen(navController: NavController) {
    val search = rememberSaveable() { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(search.value) {
        search.value.trim().isNotEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sarapan Pagi",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = mWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "arrow_back",
                            tint = mWhite
                        )
                    }
                },
                backgroundColor = mLightBlue
            )
        }

    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // SEARCH MENU
            OutlinedTextField(
                value = search.value,
                onValueChange = { search.value = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions {
                    if (!valid) return@KeyboardActions
                    // doing search functionality

                    // Log.d("search", search.value)
                    keyboardController?.hide()
                    search.value = ""
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search",
                        tint = mGrayScale
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = mLightGrayScale,
                    unfocusedBorderColor = mLightGrayScale,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    placeholderColor = mGrayScale,
                    backgroundColor = mLightGrayScale
                ),
                placeholder = {
                    Text(
                        text = "Coba ketik bubur ayam...",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
                shape = RoundedCornerShape(10.dp),
            )

            // LIST KONSUMSI
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Terakhir Dikonsumsi",
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 32.dp),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = mBlack
                )

                // Tambah Food
                TextButton(onClick = { }) {
                    Text(
                        text = "Tambah",
                        modifier = Modifier
                            .padding(bottom = 16.dp, top = 32.dp),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = mLightBlue
                    )
                }
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(10) {
                    CustomListTile()
                }
            }

        }


    }
}


@Composable
fun CustomListTile(modifier: Modifier = Modifier) {
    val checked = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = modifier.height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked.value,
                onCheckedChange = {
                    checked.value = it
                },
                modifier = modifier.weight(1f),
                colors = CheckboxDefaults.colors(
                    checkedColor = mLightBlue,
                    uncheckedColor = mLightBlue
                )
            )
            Column(
                modifier = modifier.weight(4f),
            ) {
                Text(
                    text = "Bubur Ayam",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = mBlack
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "1 porsi (100g)",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Normal,
                        color = mLightBlue
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            modifier = modifier.size(18.dp),
                            contentDescription = "edit",
                            tint = mLightBlue
                        )
                    }
                }

            }
            Text(
                text = "165 Cal",
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SearchMenuScreenPreview() {

}