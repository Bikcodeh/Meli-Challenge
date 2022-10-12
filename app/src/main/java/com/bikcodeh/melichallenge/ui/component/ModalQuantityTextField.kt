package com.bikcodeh.melichallenge.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.bikcodeh.melichallenge.R
import com.bikcodeh.melichallenge.ui.theme.*
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
                        modifier = Modifier.weight(8f)
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
                        if (value.matches("^\\d+\$".toRegex()) && value.trim().length in 1..5) {
                            quantityTextField = value
                            enabledButton = value.toInt() <= available
                        }
                        if (value.isNotEmpty()) {
                            hasError = value.toInt() > available
                        }
                        if (value.trim().isEmpty()) {
                            quantityTextField = ""
                        }
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.type_quantity))
                    }, singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = hasError
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
                Button(
                    enabled = enabledButton,
                    onClick = {
                        updateQuantity(quantityTextField.toInt())
                        quantityTextField = ""
                        onCloseModal()
                        keyboardController?.hide()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = CelticBlue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = COMMON_MINIMUM_PADDING,
                            end = COMMON_MINIMUM_PADDING,
                            bottom = COMMON_PADDING
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.add_quantity)
                    )
                }
            }
        }
    ) {}
}