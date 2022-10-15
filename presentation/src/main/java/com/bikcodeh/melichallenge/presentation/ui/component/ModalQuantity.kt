package com.bikcodeh.melichallenge.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.theme.*

private const val MORE_THAN_3_ITEMS = 3
private const val DEFAULT_TOTAL_ITEMS = 4

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun ModalQuantityListSelector(
    modalBottomSheetState: ModalBottomSheetState,
    onClose: () -> Unit,
    openTextFieldModal: () -> Unit,
    quantity: Int,
    onQuantityUpdate: (total: Int) -> Unit,
    quantitySelected: Int
) {
    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colorScheme.backgroundColor,
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = COMMON_PADDING, topEnd = COMMON_PADDING),
        sheetContent = {
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
                    color = MaterialTheme.colorScheme.textColor,
                )
                IconButton(onClick = { onClose() }, modifier = Modifier.weight(1f)) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.iconColor
                    )
                }
            }
            Divider()
            if (quantity > DEFAULT_TOTAL_ITEMS) {
                run loop@{
                    repeat(DEFAULT_TOTAL_ITEMS) {
                        val index = it
                        val currentQuantity = it + 1
                        val color = if (currentQuantity == quantitySelected) {
                            MaterialTheme.colorScheme.itemListSelected
                        } else {
                            MaterialTheme.colorScheme.itemListNotSelected
                        }
                        Text(
                            text = pluralStringResource(R.plurals.quantity_unit, currentQuantity, currentQuantity),
                            modifier = Modifier
                                .background(color)
                                .clickable {
                                    onQuantityUpdate(currentQuantity)
                                    onClose()
                                }
                                .fillMaxWidth()
                                .padding(vertical = PADDING_ITEM_MODAL),
                            textAlign = TextAlign.Center,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            color = MaterialTheme.colorScheme.textColor,
                        )
                        Divider()
                        if (index == MORE_THAN_3_ITEMS) {
                            val colorMoreThan = if (quantitySelected >= MORE_THAN_3_ITEMS) {
                                MaterialTheme.colorScheme.itemListSelected
                            } else {
                                MaterialTheme.colorScheme.itemListNotSelected
                            }
                            Text(
                                text = stringResource(id = R.string.more_than, (currentQuantity)),
                                modifier = Modifier
                                    .clickable {
                                        onClose()
                                        openTextFieldModal()
                                    }
                                    .background(colorMoreThan)
                                    .fillMaxWidth()
                                    .padding(COMMON_MINIMUM_PADDING),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.textColor
                            )
                        }
                    }
                }
            } else {
                repeat(quantity) {
                    val currentQuantity = it + 1
                    val color = if (currentQuantity == quantitySelected) {
                        MaterialTheme.colorScheme.itemListSelected
                    } else {
                        MaterialTheme.colorScheme.itemListNotSelected
                    }
                    Text(
                        text = "$currentQuantity",
                        modifier = Modifier
                            .background(color)
                            .clickable {
                                onQuantityUpdate(currentQuantity)
                                onClose()
                            }
                            .fillMaxWidth()
                            .padding(vertical = COMMON_MINIMUM_PADDING),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.textColor
                    )
                    Divider()
                }
            }
        },
    ) {}
}