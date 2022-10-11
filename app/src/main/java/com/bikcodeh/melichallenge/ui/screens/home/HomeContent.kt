package com.bikcodeh.melichallenge.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bikcodeh.melichallenge.R
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.ui.component.ErrorScreen
import com.bikcodeh.melichallenge.ui.screens.home.HomeDefaults.EMPTY_PRODUCTS_LOTTIE_SIZE
import com.bikcodeh.melichallenge.ui.screens.home.HomeDefaults.RADIUS_SEARCH
import com.bikcodeh.melichallenge.ui.screens.home.HomeDefaults.SEARCH_ITEM_IMAGE_SIZE
import com.bikcodeh.melichallenge.ui.theme.*
import com.bikcodeh.melichallenge.util.Util
import com.bikcodeh.mercadolibreapp.ui.component.Loading
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun HomeContent(
    text: String,
    onTextChange: (String) -> Unit,
    onClearSearch: (String) -> Unit,
    onCloseSearch: () -> Unit,
    onSearch: (String) -> Unit,
    onProductClick: (product: Product) -> Unit,
    homeUiState: HomeUiState,
    refreshState: Boolean,
    onRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Search(
            text = text,
            onTextChange = onTextChange,
            onClearSearch = onClearSearch,
            onSearch = onSearch,
            onCloseSearch = onCloseSearch
        )
        if (homeUiState.isLoading) {
            Loading()
        }

        homeUiState.error?.let { error ->
            ErrorScreen(error = error, onRefresh = { })
        }
        homeUiState.products?.let { products ->
            if (products.isEmpty()) {
                EmptyProducts()
            } else {
                LazyColumn() {
                    items(products.count()) { index ->
                        SearchItem(product = products[index], onProductClick)
                        if (index != products.count() - 1)
                            Divider(color = Color.LightGray)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    text: String,
    onTextChange: (String) -> Unit,
    onClearSearch: (String) -> Unit,
    onCloseSearch: () -> Unit,
    onSearch: (String) -> Unit
) {
    val focus = LocalFocusManager.current
    var focusActive by rememberSaveable() { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.statusBarColor)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
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
                    IconButton(onClick = {
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
            shape = RoundedCornerShape(RADIUS_SEARCH),
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
fun SearchItem(product: Product, onProductClick: (product: Product) -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onProductClick(product)
            }
            .fillMaxWidth()
            .padding(COMMON_MINIMUM_PADDING)
    ) {
        Card(
            modifier = Modifier.size(SEARCH_ITEM_IMAGE_SIZE)
        ) {
            CoilImage(
                product.thumbnail,
                placeHolder = painterResource(id = R.drawable.ic_image),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier.padding(
                start = COMMON_MINIMUM_PADDING,
                end = COMMON_MINIMUM_PADDING,
                bottom = COMMON_MINIMUM_PADDING
            )
        ) {
            Text(
                text = product.title, maxLines = 2, overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = MaterialTheme.colorScheme.textColor
            )
            Text(
                text = "$${Util.currencyFormatter(product.price.toInt())}",
                color = MaterialTheme.colorScheme.textColor
            )
        }
    }
}

@Composable
fun EmptyInitialState() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.search))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(COMMON_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition, modifier = Modifier.size(EMPTY_PRODUCTS_LOTTIE_SIZE))
        Text(
            text = stringResource(id = R.string.search_description),
            color = MaterialTheme.colorScheme.textColor
        )
    }
}

@Composable
fun EmptyProducts() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(COMMON_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition, modifier = Modifier.size(EMPTY_PRODUCTS_LOTTIE_SIZE))
        Text(text = stringResource(id = R.string.empty_products))
    }
}


@Composable
@Preview
fun SearchContentPreview() {
    Search(text = "", onTextChange = {}, onClearSearch = {}, onSearch = {}, onCloseSearch = {})
}

@Composable
@Preview(showBackground = true)
fun SearchItemPreview() {
    SearchItem(
        product = Product(
            id = "",
            title = "Cables de alta tension",
            price = 39999.0,
            availableQuantity = 0,
            soldQuantity = 0,
            condition = "",
            thumbnail = ""
        ),
        onProductClick = {}
    )
}
