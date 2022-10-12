package com.bikcodeh.melichallenge.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.bikcodeh.melichallenge.R
import com.bikcodeh.melichallenge.ui.theme.*

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
            if (quantity > 4) {
                run loop@{
                    repeat(4) {

                        val color = if ((it + 1) == quantitySelected) {
                            MaterialTheme.colorScheme.itemListSelected
                        } else {
                            MaterialTheme.colorScheme.itemListNotSelected
                        }
                        Text(
                            text = pluralStringResource(R.plurals.quantity_unit, it + 1, it + 1),
                            modifier = Modifier
                                .background(color)
                                .clickable {
                                    onQuantityUpdate(it + 1)
                                    onClose()
                                }
                                .fillMaxWidth()
                                .padding(vertical = PADDING_ITEM_MODAL),
                            textAlign = TextAlign.Center,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            color = MaterialTheme.colorScheme.textColor,
                        )
                        Divider()
                        if (it == 3) {
                            val colorMoreThan = if (quantitySelected >= 3) {
                                MaterialTheme.colorScheme.itemListSelected
                            } else {
                                MaterialTheme.colorScheme.itemListNotSelected
                            }
                            Text(
                                text = stringResource(id = R.string.more_than, (it + 1)),
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
                    val color = if ((it + 1) == quantitySelected) {
                        MaterialTheme.colorScheme.itemListSelected
                    } else {
                        MaterialTheme.colorScheme.itemListNotSelected
                    }
                    Text(
                        text = "${it + 1}",
                        modifier = Modifier
                            .background(color)
                            .clickable {
                                onQuantityUpdate(it + 1)
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