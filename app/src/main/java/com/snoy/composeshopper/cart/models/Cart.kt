package com.snoy.composeshopper.cart.models

import com.snoy.composeshopper.catalog.models.Item

class Cart(list: List<Item> = listOf()) {
    var items: List<Item>
        private set

    init {
        items = list
    }

    val totalPrice: Int = items.sumOf { it.price }
    val count: Int = items.size

    fun getByPosition(position: Int): Item {
        return items[position]
    }
}

fun Cart?.add(item: Item): Cart {
    return if (this == null) Cart()
    else Cart(list = this.items + item)
}

fun Cart?.remove(item: Item): Cart {
    return if (this == null) Cart()
    else Cart(list = this.items.filter { it.id != item.id })
}