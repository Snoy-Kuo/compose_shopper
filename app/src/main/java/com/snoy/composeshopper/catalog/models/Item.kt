package com.snoy.composeshopper.catalog.models

import androidx.compose.ui.graphics.Color
import com.snoy.composeshopper.ui.theme.ColorPlate

data class Item(val id: Int, val name: String) {
    val color: Color = ColorPlate[id % ColorPlate.size]
    @Suppress("unused")
    val price: Int = 42
}