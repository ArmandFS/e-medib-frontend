package com.example.e_medib.features.auth_feature.view

import CustomInputField
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_medib.features.auth_feature.model.DataRegisterModel
import com.example.e_medib.features.auth_feature.view.components.CustomLoginInputField
import com.example.e_medib.features.auth_feature.view_model.AuthViewModel
import com.example.e_medib.features.profile_feature.view.PolicyDialog
import com.example.e_medib.features.profile_feature.view_model.ProfileViewModel
import com.example.e_medib.ui.theme.mBlack
import com.example.e_medib.ui.theme.mGrayScale
import com.example.e_medib.ui.theme.mLightBlue
import com.example.e_medib.ui.theme.mLightGrayScale
import com.example.e_medib.ui.theme.mWhite
import com.example.e_medib.utils.CustomDataStore
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    isEditProfile: Boolean = false
) {
    val mContext = LocalContext.current
    val mTextFieldSize = remember { mutableStateOf(Size.Zero) }
    val mCalendar = Calendar.getInstance()

    mCalendar.time = Date()

    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val jenisKelaminExpand = remember { mutableStateOf(false) }
    val namaLengkap = rememberSaveable() { mutableStateOf("") }
    val username = rememberSaveable() { mutableStateOf("") }
    val nik = rememberSaveable() { mutableStateOf("") }
    val email = rememberSaveable() { mutableStateOf("") }
    val selectedJenisKelamin = rememberSaveable() { mutableStateOf("") }
    val usia = rememberSaveable() { mutableStateOf("") }
    val tinggiBadan = rememberSaveable() { mutableStateOf("") }
    val beratBadan = rememberSaveable() { mutableStateOf("") }
    val password = rememberSaveable() { mutableStateOf("") }
    val repeatPassword = rememberSaveable() { mutableStateOf("") }
    val showPassword = rememberSaveable() { mutableStateOf(false) }
    val showRepeatPassword = rememberSaveable() { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val agreementChecked = rememberSaveable { mutableStateOf(false) }
    var showPolicyDialog by remember { mutableStateOf(false) }
    val isValidInputs = remember(
        namaLengkap.value,
        username.value,
        nik.value,
        email.value,
        selectedJenisKelamin.value,
        usia.value,
        tinggiBadan.value,
        beratBadan.value,
        password.value,
        repeatPassword.value,
        agreementChecked.value
    ) {
        namaLengkap.value.trim().isNotEmpty() && username.value.trim()
            .isNotEmpty() && nik.value.trim().isNotEmpty() && email.value.trim()
            .isNotEmpty() && selectedJenisKelamin.value.trim().isNotEmpty() && usia.value.trim()
            .isNotEmpty() && tinggiBadan.value.trim().isNotEmpty() && beratBadan.value.trim()
            .isNotEmpty() && password.value.trim().isNotEmpty() && repeatPassword.value.trim()
            .isNotEmpty() && password.value.length > 4 && password.value == repeatPassword.value
    }


    val icon = if (jenisKelaminExpand.value) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown


    val store = CustomDataStore(mContext)
    val tokenText = store.getAccessToken.collectAsState(initial = "")


    Scaffold() {it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            if (isEditProfile) {
                // TITLE
                Text(
                    text = "Edit Data Diri",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 24.dp),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = mLightBlue
                )
            } else {
                // TITLE
                Text(
                    text = "Daftar Akun",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = mLightBlue
                )
                Text(
                    text = "Masukkan data diri anda",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = mLightBlue
                )
            }

            // NAMA LENGKAP
            Text(
                text = "Nama Lengkap",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomInputField(
                valueState = namaLengkap,
                placeholder = "Angelina Jolie",
                trailingIcon = null,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                )

            // USERNAME
            Text(
                text = "Username",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomInputField(
                valueState = username,
                placeholder = "AngelinaJolie123",
                trailingIcon = null,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            )

            // NIK
            Text(
                text = "NIK",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomInputField(
                valueState = nik,
                placeholder = "01234567890",
                trailingIcon = null,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )

            // EMAIL
            Text(
                text = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomInputField(
                valueState = email,
                placeholder = "Angelina12@gmail.com",
                trailingIcon = null,
                isEmail = true,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )

            // JENIS KELAMIN
            Text(
                text = "Jenis Kelamin",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            OutlinedTextField(
                value = selectedJenisKelamin.value,
                onValueChange = { selectedJenisKelamin.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize.value = coordinates.size.toSize()
                    },
                placeholder = {
                    Text(
                        text = "Jenis Kelamin",
                        style = MaterialTheme.typography.body1,
//                            modifier = Modifier.padding(6.dp),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        color = mGrayScale
                    )
                },
                trailingIcon = {
                    Icon(
                        icon,
                        "contentDescription",
                        tint = mGrayScale,
                        modifier = Modifier.clickable {
                            jenisKelaminExpand.value = !jenisKelaminExpand.value
                        },
                    )
                },
                readOnly = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = mWhite,
                    textColor = mBlack,
                    unfocusedBorderColor = mLightGrayScale,
                    focusedBorderColor = mLightGrayScale
                ),
            )
            if (selectedJenisKelamin.value.isEmpty()) Text(
                text = "Data tidak boleh kosong",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mLightBlue
            )
            DropdownMenu(
                expanded = jenisKelaminExpand.value,
                onDismissRequest = { jenisKelaminExpand.value = false },
                modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.value.width.toDp() })
            ) {
                jenisKelamin.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedJenisKelamin.value = label
                        jenisKelaminExpand.value = false
                    }) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            color = mGrayScale
                        )
                    }
                }
            }


            // USIA
            Text(
                text = "Usia",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomInputField(
                valueState = usia,
                placeholder = "20 Tahun",
                trailingIcon = null,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )
            // TINGGI BADAN && BERAT BADAN
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TB
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Tinggi Badan",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = mBlack
                    )
                    CustomInputField(
                        valueState = tinggiBadan,
                        placeholder = "00",
                        trailingIcon = {
                            Text(
                                text = "cm",
                                style = MaterialTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                                color = mGrayScale
                            )
                        },
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )

                }
                Spacer(modifier = Modifier.width(16.dp))

                // BB
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Berat Badan",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = mBlack
                    )
                    CustomInputField(
                        valueState = beratBadan,
                        placeholder = "00",
                        trailingIcon = {
                            Text(
                                text = "kg",
                                style = MaterialTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                                color = mGrayScale
                            )
                        },
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    )
                }
            }
            // PASSWORD
            Text(
                text = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomLoginInputField(
                valueState = password,
                passwordVisible = showPassword,
                isPassword = true,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                imeAction = ImeAction.Next,
                label = "Password",
            )
            if (password.value.length < 4) Text(
                text = "Password minimal terdiri dari 4 karakter",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mLightBlue
            )

            // REPEAT PASSWORD
            Text(
                text = "Ulangi Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = mBlack
            )
            CustomLoginInputField(
                valueState = repeatPassword,
                passwordVisible = showRepeatPassword,
                isPassword = true,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (showRepeatPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                imeAction = ImeAction.Done,
                label = "Ulangi Password",
            )
            if (password.value != repeatPassword.value) Text(
                text = "Pastikan password yang dmasukkan sama",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mLightBlue
            )
            Spacer(modifier = Modifier.height(3.dp))
            //agreement box
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                      checked = agreementChecked.value,
                      onCheckedChange = { agreementChecked.value = it},
                    )
                Text(
                    text = buildAnnotatedString {
                        append("Dengan mencentang kotak ini, Anda setuju dengan ")
                        pushStringAnnotation(
                            tag = "PrivacyPolicy",
                            annotation = "Kebijakan Keamanan Data"
                        )
                        withStyle(style = SpanStyle(color = mLightBlue)){
                            append("Kebijakan Keamanan Data")
                        }
                        pop()
                        append("dan membagikan data kesehatan diabetes pribadi Anda")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .clickable {
                            showPolicyDialog = true
                        }
                    ,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    fontSize = 12.sp,
                    color = mBlack)
            }
            Spacer(modifier = Modifier.height(22.dp))
            // BUTTON
            Button(
                onClick = {
                    keyboardController?.hide()
                    // api register
                    val registerData = DataRegisterModel(
                        namaLengkap.value,
                        username.value,
                        nik.value,
                        email.value,
                        if (selectedJenisKelamin.value == "Laki-laki") "L" else "P",
                        usia.value,
                        tinggiBadan.value,
                        beratBadan.value,
                        password.value
                    )
                    val headerMap = mutableMapOf<String, String>()
                    headerMap["Accept"] = "application/json"
                    headerMap["Authorization"] = "Bearer ${tokenText.value}"

                    if (isEditProfile) authViewModel.updateProfile(headerMap,
                        registerData,
                        mContext,
                        navigate = { navController.popBackStack() })
                    else authViewModel.doRegister(registerData, context = mContext, navigate = {
                        // Back to Login
                        navController.popBackStack()
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
                enabled = !authViewModel.isLoading && isValidInputs
            ) {
                if (isEditProfile) Text(
                    text = "Simpan",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = mWhite
                )
                else Text(
                    text = "Daftar",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = mWhite
                )
            }


            if (showPolicyDialog) {
                PolicyDialog(onDismiss = { showPolicyDialog = false })
            }

            if (!isEditProfile)
            // BALIK KE LOGIN
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sudah punya akun ?",
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Normal,
                        color = mBlack
                    )
                    TextButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Text(
                            text = "Masuk",
                            style = MaterialTheme.typography.caption,
                            fontWeight = FontWeight.SemiBold,
                            color = mLightBlue
                        )
                    }
                }

        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun RegisterScreenScreenPreview() {
    val navController = rememberNavController()

    RegisterScreen(navController)

}
