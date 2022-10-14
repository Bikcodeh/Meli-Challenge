package com.bikcodeh.melichallenge.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.theme.*
import androidx.compose.material3.MaterialTheme as MaterialTheme3

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun ModalQuantityTextField(
    modalBottomSheetState: ModalBottomSheetState,
    onCloseModal: () -> Unit,
    available: Int,
    updateQuantity: (Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var quantityTextField by rememberSaveable { mutableStateOf("") }
    var hasError by rememberSaveable { mutableStateOf(false) }
    var enabledButton by rememberSaveable { mutableStateOf(false) }

    when (modalBottomSheetState.currentValue) {
        ModalBottomSheetValue.Hidden -> {
            keyboardController?.hide()
            quantityTextField = ""
            enabledButton = false
            hasError = false
        }
        ModalBottomSheetValue.Expanded -> {}
        ModalBottomSheetValue.HalfExpanded -> {}
    }

    ModalBottomSheetLayout(
        sheetBackgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.backgroundColor,
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = COMMON_PADDING, topEnd = COMMON_PADDING),
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = COMMON_MINIMUM_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.choose_quantity),
                        modifier = Modifier.weight(8f),
                        color = MaterialTheme3.colorScheme.textColor
                    )
                    IconButton(onClick = {
                        quantityTextField = ""
                        keyboardController?.hide()
                        onCloseModal()
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme3.colorScheme.iconColor
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = COMMON_MINIMUM_PADDING),
                    text = stringResource(id = R.string.available, available),
                    color = MaterialTheme3.colorScheme.textColor,
                    fontSize = FONT_SMALL
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = COMMON_MINIMUM_PADDING,
                            vertical = COMMON_MINIMUM_PADDING
                        ),
                    value = quantityTextField,
                    onValueChange = { value ->
                        val isValueValid = value.matches("^\\d+\$".toRegex())
                        if (isValueValid && value.trim().length in 1..5) {
                            quantityTextField = value
                            enabledButton = value.toInt() <= available
                        }
                        if (value.isNotEmpty() && isValueValid) {
                            hasError = value.toInt() > available
                        }
                        if (value.trim().isEmpty()) {
                            quantityTextField = ""
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.type_quantity),
                            color = MaterialTheme3.colorScheme.textColor,
                        )
                    }, singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = hasError,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme3.colorScheme.textColor,
                        cursorColor = MaterialTheme3.colorScheme.textColor,
                        focusedIndicatorColor = MaterialTheme3.colorScheme.textColor
                    )
                )
                if (hasError) {
                    Text(
                        text = stringResource(id = R.string.not_stock),
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = COMMON_MINIMUM_PADDING)
                    )
                }
                ActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = COMMON_MINIMUM_PADDING,
                            end = COMMON_MINIMUM_PADDING,
                            bottom = COMMON_PADDING
                        ),
                    buttonColors = ButtonDefaults.buttonColors(
                        backgroundColor = CelticBlue,
                        contentColor = Color.White
                    ),
                    textButton = R.string.add_quantity,
                    onClick = {
                        updateQuantity(quantityTextField.toInt())
                        quantityTextField = ""
                        onCloseModal()
                        keyboardController?.hide()
                    },
                    isEnabled = enabledButton
                )
            }
        }
    ) {}
}
