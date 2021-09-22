package com.snoy.composeshopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.snoy.composeshopper.catalog.CatalogScreen
import com.snoy.composeshopper.ui.theme.ComposeShopperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which means we need to through handling
        // insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ComposeShopperTheme {
                ProvideWindowInsets {
                    CatalogScreen()
                }
            }
        }
    }
}
