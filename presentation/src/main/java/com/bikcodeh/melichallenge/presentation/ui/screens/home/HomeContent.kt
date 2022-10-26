package com.bikcodeh.melichallenge.presentation.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bikcodeh.melichallenge.domain.common.toError
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.component.*
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeDefaults.SEARCH_ITEM_IMAGE_SIZE
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeTestTags.ITEM_CONTAINER
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeTestTags.ITEM_NAME
import com.bikcodeh.melichallenge.presentation.ui.theme.*
import com.bikcodeh.melichallenge.presentation.ui.util.ErrorLoadState
import com.bikcodeh.melichallenge.presentation.util.Util
import com.bikcodeh.melichallenge.domain.R as RD

@Composable
fun HomeContent(
    text: String,
    onTextChange: (String) -> Unit,
    onClearSearch: (String) -> Unit,
    onCloseSearch: () -> Unit,
    onSearch: (String) -> Unit,
    onProductClick: (product: Product) -> Unit,
    homeUiState: HomeUiState,
    products: LazyPagingItems<Product>
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
        val errorState = handlePagingResult(products = products)

        if (errorState.isRefresh) {
            errorState.error?.let {
                ErrorScreen(
                    messageId = handleError(error = it),
                    modifier = Modifier.fillMaxSize(),
                    onTryAgain = { products.retry() },
                    displayTryButton = true
                )
            }
        } else {
            if (homeUiState.initialState) {
                GenericMessageScreen(
                    modifier = Modifier.testTag(HomeTestTags.INITIAL_SCREEN),
                    textModifier = Modifier.testTag(HomeTestTags.MESSAGE_GENERIC_SCREEN),
                    lottieId = R.raw.search,
                    messageId = R.string.search_description
                )
            } else {
                if (products.loadState.refresh is LoadState.Loading) {
                    Loading()
                }
                LazyColumn(modifier = Modifier.testTag(HomeTestTags.CONTAINER_PRODUCTS)) {
                    items(products) { product ->
                        product?.let {
                            SearchItem(product = product, onProductClick)
                        }
                    }
                    item {
                        if (errorState.isAppend) {
                            ErrorMoreRetry(
                                onRetry = { products.retry() }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ErrorMoreRetry(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.backgroundColor),
    ) {
        Text(
            text = stringResource(id = R.string.error_fetching_data),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = COMMON_PADDING, end = COMMON_PADDING, top = COMMON_PADDING),
            color = MaterialTheme.colorScheme.textColor,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onRetry() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(COMMON_PADDING),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CelticBlue
            )
        ) {
            Text(
                text = stringResource(id = RD.string.try_again).uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMoreRetryPreview() {
    ErrorMoreRetry(onRetry = {})
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ErrorMoreRetryPreviewDark() {
    ErrorMoreRetry(onRetry = {})
}

@Composable
fun handlePagingResult(
    products: LazyPagingItems<Product>
): ErrorLoadState {
    val errorLoadState = ErrorLoadState()
    products.apply {
        val stateError = when {
            loadState.refresh is LoadState.Error -> {
                errorLoadState.isRefresh = true
                loadState.refresh as LoadState.Error
            }
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> {
                errorLoadState.isAppend = true
                loadState.append as LoadState.Error
            }
            else -> null
        }
        val error = stateError?.error?.toError()
        errorLoadState.error = error
        return errorLoadState
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
