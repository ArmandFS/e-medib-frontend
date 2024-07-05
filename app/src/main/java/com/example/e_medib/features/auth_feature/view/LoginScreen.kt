package com.example.e_medib.features.auth_feature.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_medib.R
import com.example.e_medib.features.auth_feature.model.LoginModel
import com.example.e_medib.features.auth_feature.view.components.CustomLoginInputField
import com.example.e_medib.features.auth_feature.view_model.AuthViewModel
import com.example.e_medib.navigations.AppScreen
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mLightBlue
import com.example.e_medib.ui.theme.mLightGrayScale
import com.example.e_medib.ui.theme.mWhite

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun LoginScreen(navController: NavController) {
    Scaffold {
        LoginScreenComponent(navController, onDone = { username, password ->
            Log.d("Login", "$username || $password")
        })
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreenComponent(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    onDone: (String, String) -> Unit = { username, password -> }
) {
    val username = rememberSaveable() { mutableStateOf("") }
    val password = rememberSaveable() { mutableStateOf("") }
    val showPassword = rememberSaveable() { mutableStateOf(false) }
    val isLoading = remember() { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValidEmailOrPassword = remember(username.value, password.value) {
        username.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // WELCOME
        Image(
            painter = painterResource(id = R.drawable.app_logo_blue),
            contentDescription = "app_logo", modifier = Modifier.size(60.dp)
        )
        Text(
            text = "Selamat Datang",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = mLightBlue
        )
        Text(
            text = "Silahkan masuk ke akun anda",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.Normal,
            color = mLightBlue
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Username
        Text(
            text = "Username",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.Normal,
            color = mBlack,
            textAlign = TextAlign.Left
        )
        CustomLoginInputField(
            valueState = username, label = "username",
            imeAction = ImeAction.Next,
        )
        Spacer(modifier = Modifier.height(16.dp))

        // password
        Text(
            text = "Password",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.Normal,
            color = mBlack,
            textAlign = TextAlign.Left
        )
        CustomLoginInputField(
            valueState = password,
            passwordVisible = showPassword,
            isPassword = true,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            imeAction = ImeAction.Done,
            label = "password",
            onAction = KeyboardActions {
                if (!isValidEmailOrPassword) return@KeyboardActions
                onDone(username.value, password.value)
                keyboardController?.hide()
            })
        Spacer(modifier = Modifier.height(32.dp))


        // button
        Button(
            onClick = {
                onDone(username.value.trim(), password.value.trim())
                keyboardController?.hide()
                val loginData = LoginModel(username = username.value, password = password.value)

                authViewModel.doLogin(loginData, context = mContext, navigate = {
                    navController.navigate(AppScreen.Beranda.screen_route)
                })

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = mLightBlue,
                contentColor = mWhite,
                disabledBackgroundColor = mLightGrayScale,
                disabledContentColor = mBlack
            ),
            shape = RoundedCornerShape(32.dp),
            enabled = !authViewModel.isLoading && isValidEmailOrPassword
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = mWhite
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // go to register
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Belum punya akun ?",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mBlack
            )
            TextButton(onClick = {
                navController.navigate(AppScreen.RegisterScreen.screen_route + "/${false}")
            }) {
                Text(
                    text = "Daftar",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.SemiBold,
                    color = mLightBlue
                )
            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun DefaultPreview() {
//    LoginScreenComponent()
}