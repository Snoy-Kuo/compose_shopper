@file:Suppress("MemberVisibilityCanBePrivate")

package com.snoy.composeshopper.catalog.models

object Catalog {
    private val itemNames = listOf(
        "Item A",
        "Item B",
        "Item C",
        "Item D",
        "Item E",
        "Item F",
        "Item G",
        "Item H",
        "Item I",
        "Item J",
        "Item K",
        "Item L",
        "Item M",
        "Item N",
        "Item O",
    )

    val count: Int = itemNames.size

    fun getById(id: Int): Item {
        return Item(id, itemNames[id % itemNames.size])
    }

    fun getByPosition(position: Int): Item {
        return getById(position)
    }
}
