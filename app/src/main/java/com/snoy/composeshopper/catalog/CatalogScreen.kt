package com.snoy.composeshopper.catalog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.snoy.composeshopper.R
import com.snoy.composeshopper.catalog.models.Catalog
import com.snoy.composeshopper.catalog.models.Item
import com.snoy.composeshopper.ui.theme.ComposeShopperTheme

@Composable
fun CatalogScreen() {
    Scaffold(
        topBar = {
            // We use TopAppBar from accompanist-insets-ui which allows us to provide
            // content padding matching the system bars insets.
            TopAppBar(
                title = { Text(stringResource(R.string.catalog)) },
                backgroundColor = MaterialTheme.colors.primary,//.copy(alpha = 0.95f),
                contentPadding = rememberInsetsPaddingValues(
                    LocalWindowInsets.current.statusBars,
                    applyBottom = false,
                ),
                actions = {
                    IconButton(onClick = {
                        Log.d("RDTest", "Cart icon clicked!")
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
        Box {
            // We apply the contentPadding passed to us from the Scaffold
            LazyColumn(
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxSize(),
            ) {
//                items(items = listItems) { imageUrl ->
//                    ListItem(imageUrl, Modifier.fillMaxWidth())
//                }
                // Add Catalog.count items
                items(Catalog.count) { index ->
                    MyListItem(Catalog.getByPosition(index))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeShopperTheme {
        ProvideWindowInsets {
            CatalogScreen()
        }
    }
}

@Composable
private fun MyListItem(item: Item) {
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
        TextButton(onClick = {
            Log.d("RDTest", "on item ${item.id} add click.")
        }) {
            Text(text = "ADD", style = MaterialTheme.typography.button)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    ComposeShopperTheme {
        MyListItem(Item(id = 0, name = "NAME"))
    }
}
