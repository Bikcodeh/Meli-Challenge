package com.bikcodeh.melichallenge.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeDefaults
import com.bikcodeh.melichallenge.presentation.ui.theme.COMMON_PADDING
import com.bikcodeh.melichallenge.presentation.ui.theme.backgroundColorTextField
import com.bikcodeh.melichallenge.presentation.ui.theme.iconColor
import com.bikcodeh.melichallenge.presentation.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onClearSearch: (String) -> Unit,
    onCloseSearch: () -> Unit,
    onSearch: (String) -> Unit
) {
    val focus = LocalFocusManager.current
    var focusActive by rememberSaveable() { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .testTag(SearchTestTags.INPUT)
                .fillMaxWidth()
                .padding(COMMON_PADDING)
                .onFocusChanged { focusState ->
                    focusActive = focusState.hasFocus
                },
            maxLines = 1,
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            trailingIcon = {

                if (text.isNotEmpty() || focusActive) {
                    IconButton(
                        modifier = Modifier.testTag(SearchTestTags.INPUT_CLOSE_BUTTON),
                        onClick = {
                            if (text.isNotEmpty() && !focusActive) {
                                onClearSearch(text)
                                focus.moveFocus(FocusDirection.Right)
                            } else {
                                onClearSearch(text)
                            }

                            if (text.isEmpty()) {
                                focus.clearFocus()
                                onCloseSearch()
                            }
                        }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.clear_search_description)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(HomeDefaults.RADIUS_SEARCH),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_in_meli),
                    color = MaterialTheme.colorScheme.textColor
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (text.isNotEmpty()) {
                        onSearch(text)
                        focus.clearFocus()
                    }
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.backgroundColorTextField,
                textColor = MaterialTheme.colorScheme.textColor,
                focusedTrailingIconColor = MaterialTheme.colorScheme.iconColor,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.iconColor,
                focusedLeadingIconColor = MaterialTheme.colorScheme.iconColor,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.iconColor,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}

@Composable
@Preview
fun SearchContentPreview() {
    Search(text = "", onTextChange = {}, onClearSearch = {}, onSearch = {}, onCloseSearch = {})
}

object SearchTestTags {
    const val CONTAINER = "SearchContainer"
    const val INPUT = "SearchInput"
    const val INPUT_CLOSE_BUTTON = "InputCloseButton"
}