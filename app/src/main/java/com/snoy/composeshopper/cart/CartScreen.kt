package com.snoy.composeshopper.cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.snoy.composeshopper.R
import com.snoy.composeshopper.cart.models.Cart
import com.snoy.composeshopper.catalog.models.Item
import com.snoy.composeshopper.common.FullScreenText
import com.snoy.composeshopper.common.repo.Result
import com.snoy.composeshopper.ui.theme.ComposeShopperTheme
import kotlinx.coroutines.launch

//ref = https://developer.android.com/jetpack/compose/libraries
@Composable
fun CartScreen(
    viewModel: CartViewModel,
    onBackPress: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    //ref=https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#Snackbar(androidx.compose.material.SnackbarData,androidx.compose.ui.Modifier,kotlin.Boolean,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            // We use TopAppBar from accompanist-insets-ui which allows us to provide
            // content padding matching the system bars insets.
            TopAppBar(
                title = { Text(stringResource(R.string.cart)) },
                backgroundColor = MaterialTheme.colors.surface,
                contentPadding = rememberInsetsPaddingValues(
                    LocalWindowInsets.current.statusBars,
                    applyBottom = false,
                ),
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPress()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack, contentDescription = "ArrowBack",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }

            )
        },
        bottomBar = {
            // We add a spacer as a bottom bar, which is the same height as
            // the navigation bar
            Spacer(
                Modifier
                    .navigationBarsHeight()
                    .fillMaxWidth()
            )
        },
    ) { contentPadding ->
        when (state) {
            is Result.Success<Cart> -> {
                CartBody(
                    modifier = Modifier.padding(contentPadding),
                    cart = (state as Result.Success<Cart>).data,
                    onItemDeletePress = { item ->
                        Log.d("RDTest", "Cart ${item.name} delete clicked!")
                        viewModel.removeCartItem(item)
                    },
                    onBuyPress = {
                        // show snackbar as a suspend function
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Buying is not supported yet.")
                        }
                    }
                )
            }
            is Result.Error -> {
                FullScreenText("Something went wrong!")
            }
            is Result.Loading -> {
                FullScreenText("Loading...")
            }
        }
    }
}

@Composable
private fun CartBody(
    modifier: Modifier,
    cart: Cart,
    onItemDeletePress: (item: Item) -> Unit = {},
    onBuyPress: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .wrapContentSize(Alignment.Center),
    ) {
        CartList(
            Modifier.weight(1f),
            cart = cart,
            onItemDeletePress = onItemDeletePress
        )
        Divider(thickness = 4.dp, color = MaterialTheme.colors.onPrimary)

        CartTotal(cart, onBuyPress)
    }
}

@Composable
private fun CartList(
    modifier: Modifier,
    cart: Cart,
    onItemDeletePress: (item: Item) -> Unit = {}
) {
    Box(modifier = modifier.padding(32.dp)) {
        // We apply the contentPadding passed to us from the Scaffold
        if (0 == cart.count) {
            FullScreenText("Nothing in the cart.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(cart.count) { index ->
                    CartListItem(cart.getByPosition(index), onDeletePress = onItemDeletePress)
                }
            }
        }
    }
}

@Composable
private fun CartListItem(item: Item, onDeletePress: (item: Item) -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.Done,
            contentDescription = "Done",
            tint = MaterialTheme.colors.onPrimary
        )

        Spacer(Modifier.width(24.dp))
        Text(
            text = item.name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.onPrimary
        )
        Spacer(Modifier.width(24.dp))
        IconButton(onClick = {
            onDeletePress(item)
        }) {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    ComposeShopperTheme {
        CartListItem(Item(id = 0, name = "NAME"))
    }
}

@Composable
private fun CartTotal(cart: Cart, onBuyPress: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .wrapContentSize(Alignment.Center),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        Text(
            text = "${cart.totalPrice}",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary
        )
        Spacer(Modifier.width(24.dp))
        Button(
            onClick = {
                onBuyPress()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
        ) {
            Text(
                "BUY",
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeShopperTheme {
        ProvideWindowInsets {
            CartScreen(viewModel = viewModel(factory = CartViewModelFactory()))
        }
    }
}