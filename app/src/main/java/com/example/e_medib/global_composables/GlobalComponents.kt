import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.e_medib.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomExpandedCard(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = true,
    header: @Composable @UiComposable () -> Unit,
    body: @Composable @UiComposable () -> Unit,
) {
    val expandedState = remember() { mutableStateOf(false) }
    val icon = Icons.Default.KeyboardArrowDown
    val rotationState by animateFloatAsState(targetValue = if (expandedState.value) 180f else 0f)

    Card(modifier = modifier
        .fillMaxWidth()
        .animateContentSize(
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color = mLightGrayScale),
        elevation = 0.dp,
        onClick = { expandedState.value = !expandedState.value }) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // header
            header()

            if (isExpanded) {
                Divider(
                    modifier = Modifier.padding(top = 12.dp),
                    color = mLightGrayScale,
                    thickness = 1.dp
                )

                // body
                if (expandedState.value) body()


                // footer
                IconButton(modifier = Modifier
                    .padding(top = 8.dp)
                    .rotate(rotationState),
                    onClick = { expandedState.value = !expandedState.value }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "arrow up",
                        tint = mLightBlue,
                    )
                }
            }


        }


    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}


@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    placeholder: String,
    isEnable: Boolean = true,
    isSingleLine: Boolean = true,
    readOnly: Boolean = false,
    isEmail: Boolean = false,
    useValidation: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable() (() -> Unit)?,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = valueState.value, onValueChange = { valueState.value = it },
            placeholder = { Text(text = placeholder, style = MaterialTheme.typography.caption) },
            singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            enabled = isEnable,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = onAction,
            shape = RoundedCornerShape(10.dp),
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = mLightGrayScale,
                unfocusedBorderColor = mLightGrayScale,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                placeholderColor = mGrayScale
            ),
            modifier = modifier.fillMaxWidth(),
        )
        if (useValidation && valueState.value.isEmpty()) {
            Text(
                text = "Data tidak boleh kosong",
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                color = mLightBlue
            )
        } else if (useValidation && isEmail) {
            if (!isValidEmail(valueState.value)) {
                Text(
                    text = "Email tidak valid",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Normal,
                    color = mLightBlue
                )
            }
        }
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    state: com.dokar.sheets.BottomSheetState,
    isEnable: Boolean,
    textFieldTitle: String,
    onClick: () -> Unit,
    title: String = "Masukan Data",
    body: @Composable @UiComposable () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    com.dokar.sheets.BottomSheet(state) {
        Column(
            modifier = modifier
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // TITLE
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = mBlack
            )

            // BODY TEXTFIELD
            body()

            // BUTTON
            Button(
                onClick = {
                    keyboardController?.hide()
                    onClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = mLightBlue,
                    contentColor = mWhite,
                    disabledBackgroundColor = mLightGrayScale,
                    disabledContentColor = mBlack
                ),
                shape = RoundedCornerShape(32.dp),
                enabled = isEnable
            ) {
                Text(
                    text = "Simpan",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    color = mWhite
                )
            }

        }
    }
}

@Composable
fun CustomLoadingOverlay() {
    Dialog(
        onDismissRequest = { }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = mLightBlue)
            }
        }
    }
}