package com.snoy.composeshopper.catalog.models

class Catalog(list: List<Item> = listOf()) {
    private var items: List<Item> = list

    val count: Int = items.size

    fun getByPosition(position: Int): Item {
        return items[position]

    }
}
