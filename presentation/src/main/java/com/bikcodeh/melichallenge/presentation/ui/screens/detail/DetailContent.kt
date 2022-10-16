package com.bikcodeh.melichallenge.presentation.ui.screens.detail

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.component.ActionButton
import com.bikcodeh.melichallenge.presentation.ui.component.ModalQuantityListSelector
import com.bikcodeh.melichallenge.presentation.ui.component.ModalQuantityTextField
import com.bikcodeh.melichallenge.presentation.ui.theme.*
import com.bikcodeh.melichallenge.presentation.util.Util
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme as MaterialTheme3

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun DetailContent(
    product: Product,
    onBack: () -> Unit,
    productDescriptionState: ProductDescriptionUiState
) {
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomTextFieldSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var quantitySelected by rememberSaveable { mutableStateOf("1") }

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            DetailTopBar(onBack)
        }) { _ ->
        Column(
            modifier = Modifier
                .testTag(DetailTestTags.CONTAINER)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme3.colorScheme.backgroundColor)
                .padding(COMMON_PADDING)
        ) {
            Row() {
                Text(
                    text = "${product.condition.capitalize(Locale.current)} | ${product.soldQuantity} sold",
                    fontStyle = MaterialTheme.typography.body1.fontStyle,
                    fontSize = FONT_SMALL,
                    color = Color.Gray,
                    modifier = Modifier.testTag(DetailTestTags.CONDITION)
                )
            }
            Text(
                text = product.title, modifier = Modifier
                    .testTag(DetailTestTags.TITLE)
                    .fillMaxWidth()
                    .padding(vertical = COMMON_MINIMUM_PADDING),
                color = MaterialTheme3.colorScheme.textColor
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_image),
                error = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HEIGHT_DETAIL_IMAGE)
            )
            Text(
                text = "$${Util.currencyFormatter(product.price.toInt())}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = COMMON_MINIMUM_PADDING),
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = MaterialTheme3.colorScheme.textColor
            )
            Text(
                text = stringResource(id = R.string.free_shipping),
                color = ShinyShamrock,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = COMMON_MINIMUM_PADDING)
            )
            QuantitySelection(
                quantity = product.availableQuantity,
                openModal = {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                },
                quantitySelected = quantitySelected
            )

            ActionButton(
                modifier = Modifier
                    .testTag(DetailTestTags.BUTTON_ADD_TO_CART)
                    .fillMaxWidth()
                    .padding(top = COMMON_PADDING),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = AzureishWhite,
                    contentColor = CelticBlue
                ),
                textButton = R.string.add_to_cart,
                onClick = {}
            )
            ActionButton(
                modifier = Modifier
                    .testTag(DetailTestTags.BUTTON_BUY_NOW)
                    .fillMaxWidth()
                    .padding(bottom = COMMON_PADDING),
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = CelticBlue,
                    contentColor = Color.White
                ),
                textButton = R.string.buy_now,
                onClick = {}
            )
            SnackbarHost(
                hostState = snackbarHostState
            )
            Text(
                text = stringResource(id = R.string.description),
                fontSize = MaterialTheme.typography.h6.fontSize,
                color = MaterialTheme3.colorScheme.textColor,
                fontWeight = FontWeight.Bold
            )
            val messageDescription = if (!productDescriptionState.hasError) {
                productDescriptionState.description
            } else {
                stringResource(id = R.string.error_description)
            }
            Text(
                text = messageDescription,
                color = MaterialTheme3.colorScheme.textColor,
                modifier = Modifier
                    .testTag(DetailTestTags.DESCRIPTION)
                    .fillMaxWidth()
            )
        }
        ModalQuantityListSelector(
            modalBottomSheetState = bottomSheetState,
            openTextFieldModal = {
                coroutineScope.launch {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        bottomTextFieldSheetState.show()
                    }
                }
            },
            onClose = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
            },
            quantity = product.availableQuantity,
            onQuantityUpdate = {
                quantitySelected = it.toString()
            },
            quantitySelected = quantitySelected.toInt()
        )
        ModalQuantityTextField(
            modalBottomSheetState = bottomTextFieldSheetState,
            onCloseModal = {
                coroutineScope.launch {
                    bottomTextFieldSheetState.hide()
                }
            },
            available = product.availableQuantity,
            updateQuantity = {
                quantitySelected = it.toString()
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun QuantitySelection(
    quantity: Int,
    openModal: () -> Unit,
    quantitySelected: String
) {
    val annotatedText = buildAnnotatedString {
        append(stringResource(id = R.string.quantity, quantitySelected))
        append("    ")
        withStyle(
            style = SpanStyle(
                color = Color.DarkGray.copy(alpha = 0.6f)
            )
        ) {
            append(stringResource(id = R.string.available, quantity.toString()))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    openModal()
                },
            shape = MaterialTheme.shapes.medium,
            color = Color.LightGray.copy(alpha = 0.6f)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = COMMON_PADDING)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(7f),
                        text = annotatedText,
                        fontSize = FONT_SMALL,
                        style = TextStyle(
                            background = Color.Transparent
                        )
                    )
                    Icon(
                        modifier = Modifier.weight(1f),
                        imageVector = Icons.Default.ArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DetailContentPreview() {
    DetailContent(
        product = Product(
            id = "",
            title = "Hello world",
            price = 99999.0,
            availableQuantity = 5,
            soldQuantity = 125,
            condition = "New",
            thumbnail = ""
        ),
        productDescriptionState = ProductDescriptionUiState(
            description = "Description message"
        ),
        onBack = {}
    )
}