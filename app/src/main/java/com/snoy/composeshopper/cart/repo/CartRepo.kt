package com.snoy.composeshopper.cart.repo

import com.snoy.composeshopper.cart.models.Cart
import com.snoy.composeshopper.catalog.models.Item
import com.snoy.composeshopper.common.repo.Result
import kotlinx.coroutines.flow.StateFlow

interface CartRepo {
    fun observeCart(): StateFlow<Result<Cart>>
    suspend fun loadCart()
    suspend fun addCartItem(item: Item)
    suspend fun removeCartItem(item: Item)
}