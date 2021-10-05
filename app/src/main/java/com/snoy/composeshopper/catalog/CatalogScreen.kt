package com.snoy.composeshopper.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.snoy.composeshopper.catalog.models.Catalog
import com.snoy.composeshopper.catalog.models.Item
import com.snoy.composeshopper.common.FullScreenText
import com.snoy.composeshopper.common.repo.Result
import com.snoy.composeshopper.ui.theme.ComposeShopperTheme

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    onCartClick: () -> Unit = {},
) {
    val catalogState by viewModel.catalogState.collectAsState()
    val cartState by viewModel.cartState.collectAsState()

    Scaffold(
        topBar = {
            // We use TopAppBar from accompanist-insets-ui which allows us to provide
            // content padding matching the system bars insets.
            TopAppBar(
                title = { Text(stringResource(R.string.catalog)) },
                backgroundColor = MaterialTheme.colors.primary,
                contentPadding = rememberInsetsPaddingValues(
                    LocalWindowInsets.current.statusBars,
                    applyBottom = false,
                ),
                actions = {
                    IconButton(onClick = {
                        onCartClick()
                    }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "ShoppingCart")
                    }
                },
                modifier = Modifier.fillMaxWidth()
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
        when (catalogState) {
            is Result.Success<Catalog> -> {
                Box {
                    // We apply the contentPadding passed to us from the Scaffold
                    LazyColumn(
                        contentPadding = contentPadding,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val catalog = (catalogState as Result.Success<Catalog>).data
                        // Add Catalog.count items
                        items(catalog.count) { index ->
                            CatalogListItem(
                                item = catalog.getByPosition(index),
                                cartState = cartState,
                                onAddPress = { item ->
                                    viewModel.addCartItem(item)
                                })
                        }
                    }
                }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeShopperTheme {
        ProvideWindowInsets {
            CatalogScreen(viewModel = viewModel(factory = CatalogViewModelFactory()))
        }
    }
}

@Composable
private fun CatalogListItem(
    item: Item,
    cartState: Result<Cart> = Result.Loading,
    onAddPress: (item: Item) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(ratio = 1f)
                .background(color = item.color)
        )
        Spacer(Modifier.width(24.dp))
        Text(text = item.name, style = MaterialTheme.typography.h6, modifier = Modifier.weight(1f))
        Spacer(Modifier.width(24.dp))
        when (cartState) {
            is Result.Success<Cart> -> {
                val cart = cartState.data
                AddButton(
                    item = item,
                    inCart = cart.items.contains(item),
                    onAddPress = { item -> onAddPress(item) }
                )
            }
            is Result.Error -> {
                Icon(
                    Icons.Filled.Warning, contentDescription = "Warning",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            is Result.Loading -> {
                Icon(
                    Icons.Filled.Refresh, contentDescription = "Loading",
                    tint = MaterialTheme.colors.onSurface
                )
            }

        }
    }
}

@Composable
private fun AddButton(item: Item, inCart: Boolean = false, onAddPress: (item: Item) -> Unit = {}) {
    if (inCart) {
        Icon(
            Icons.Filled.Check, contentDescription = "Added",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.width(48.dp),
        )
    } else {
        TextButton(
            modifier = Modifier.width(48.dp),
            onClick = {
                onAddPress(item)
            }) {
            Text(text = "ADD", style = MaterialTheme.typography.button)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    ComposeShopperTheme {
        CatalogListItem(Item(id = 0, name = "NAME"))
    }
}
