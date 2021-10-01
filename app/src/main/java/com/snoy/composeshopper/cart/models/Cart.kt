package com.snoy.composeshopper.cart.models

import com.snoy.composeshopper.catalog.models.Item

class Cart(private val items: List<Item> = listOf()) {
    val totalPrice: Int = items.sumOf { it.price }
    val count: Int = items.size

    fun getByPosition(position: Int): Item {
        return items[position]
    }
}