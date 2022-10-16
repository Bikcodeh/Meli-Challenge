package com.bikcodeh.melichallenge.presentation.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.component.*
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeDefaults.RADIUS_SEARCH
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeDefaults.SEARCH_ITEM_IMAGE_SIZE
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeTestTags.ITEM_CONTAINER
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeTestTags.ITEM_NAME
import com.bikcodeh.melichallenge.presentation.ui.theme.*
import com.bikcodeh.melichallenge.presentation.util.Util

@Composable
fun HomeContent(
    text: String,
    onTextChange: (String) -> Unit,
    onClearSearch: (String) -> Unit,
    onCloseSearch: () -> Unit,
    onSearch: (String) -> Unit,
    onProductClick: (product: Product) -> Unit,
    homeUiState: HomeUiState,
    onRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Search(
            modifier = Modifier
                .testTag(SearchTestTags.CONTAINER)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.statusBarColor),
            text = text,
            onTextChange = onTextChange,
            onClearSearch = onClearSearch,
            onSearch = onSearch,
            onCloseSearch = onCloseSearch
        )
        if (homeUiState.isLoading) {
            Loading()
        }

        if (homeUiState.initialState) {
            GenericMessageScreen(
                modifier = Modifier.testTag(HomeTestTags.INITIAL_SCREEN),
                textModifier = Modifier.testTag(HomeTestTags.MESSAGE_GENERIC_SCREEN),
                lottieId = R.raw.search,
                messageId = R.string.search_description
            )
        } else {
            homeUiState.products?.let { products ->
                if (products.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.testTag(HomeTestTags.CONTAINER_PRODUCTS)) {
                        items(homeUiState.products.count()) { index ->
                            SearchItem(product = homeUiState.products[index], onProductClick)
                            if (index != homeUiState.products.count() - 1)
                                Divider(color = Color.LightGray)
                        }
                    }
                } else {
                    GenericMessageScreen(
                        modifier = Modifier.testTag(HomeTestTags.EMPTY_SCREEN),
                        textModifier = Modifier.testTag(HomeTestTags.MESSAGE_GENERIC_SCREEN),
                        lottieId = R.raw.empty,
                        messageId = R.string.empty_products
                    )
                }
            }
        }

        homeUiState.error?.let { error ->
            ErrorScreen(error = error, onRefresh = onRefresh)
        }
    }
}

@Composable
fun SearchItem(product: Product, onProductClick: (product: Product) -> Unit) {
    Row(
        modifier = Modifier
            .testTag(ITEM_CONTAINER)
            .background(MaterialTheme.colorScheme.backgroundColor)
            .clickable {
                onProductClick(product)
            }
            .fillMaxWidth()
            .padding(COMMON_MINIMUM_PADDING)
    ) {
        Card(
            modifier = Modifier.size(SEARCH_ITEM_IMAGE_SIZE)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_image),
                error = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
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
                color = MaterialTheme.colorScheme.textColor,
                modifier = Modifier.testTag(ITEM_NAME)
            )
            Text(
                text = "$${Util.currencyFormatter(product.price.toInt())}",
                color = MaterialTheme.colorScheme.textColor
            )
        }
    }
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

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun SearchItemPreviewDark() {
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
