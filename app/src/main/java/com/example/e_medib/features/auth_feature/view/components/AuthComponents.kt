package com.example.e_medib.features.auth_feature.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_medib.ui.theme.mGrayScale
import com.example.e_medib.ui.theme.mLightGrayScale

@Composable
fun CustomLoginInputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    isEnable: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    passwordVisible: MutableState<Boolean> = rememberSaveable() { mutableStateOf(false) },
    isPassword: Boolean = false,
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        placeholder = { Text(text = "Masukan $label") },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        enabled = isEnable,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        shape = RoundedCornerShape(10.dp),
        visualTransformation = visualTransformation,
        trailingIcon = {
            val image = if (passwordVisible.value)
                Icons.Outlined.Visibility
            else Icons.Outlined.VisibilityOff

            val description = if (passwordVisible.value) "Hide password" else "Show password"

            if (isPassword) IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(imageVector = image, description)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = mLightGrayScale,
            unfocusedBorderColor = mLightGrayScale,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            placeholderColor = mGrayScale
        ),
        modifier = modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
    )
}

