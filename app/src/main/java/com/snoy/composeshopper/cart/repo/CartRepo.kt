package com.snoy.composeshopper.cart.repo

import com.snoy.composeshopper.cart.models.Cart
import com.snoy.composeshopper.catalog.models.Item

/**
 * A fake repo returning sample data
 */
object CartRepo {
    fun getCart(): Cart {
        return Cart(items = getFakeList())
    }

    private fun getFakeList(): List<Item> {
        val itemNames = listOf(
            "Item A",
            "Item C",
            "Item F",
            "Item J",
            "Item O",
        )
        return itemNames.map { Item(id = it.last().uppercaseChar().code - 'A'.code, name = it) }
    }
}