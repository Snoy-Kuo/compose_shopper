package com.snoy.composeshopper.cart.repo

import com.snoy.composeshopper.cart.models.Cart
import com.snoy.composeshopper.cart.models.add
import com.snoy.composeshopper.cart.models.remove
import com.snoy.composeshopper.catalog.models.Item
import com.snoy.composeshopper.common.repo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

/**
 * A fake repo returning sample data
 */
object FakeCartRepo : CartRepo {
    private var cartData: Cart? = null
    private val _result = MutableStateFlow<Result<Cart>>(Result.Loading)
    private val result: StateFlow<Result<Cart>> = _result

    private fun getFakeList(): List<Item> {
        val itemNames = listOf(
            "Item A",
            "Item B",
            "Item C",
            "Item D",
//            "Item E",
        )
        return itemNames.map { Item(id = it.last().uppercaseChar().code - 'A'.code, name = it) }
    }

    override fun observeCart(): StateFlow<Result<Cart>> {
        return result
    }

    override suspend fun loadCart() {
        _result.value = Result.Loading

        if (cartData != null) {
            _result.value = Result.Success(cartData!!)
            return
        }

        try {
            val data = withContext(Dispatchers.IO) {
//                service.loadCart()
                delay(500)
                Cart(getFakeList())
            }
            cartData = data

            _result.value = Result.Success(data)
        } catch (e: Exception) {
            _result.value = Result.Error(e)
        }
    }

    override suspend fun addCartItem(item: Item) {
        try {
            val data = withContext(Dispatchers.IO) {
//                service.addCartItem(item)
                delay(100)

                cartData.add(item)
            }
            cartData = data

            _result.value = Result.Success(data)
        } catch (e: Exception) {
            _result.value = Result.Error(e)
        }
    }

    override suspend fun removeCartItem(item: Item) {
        try {
            val data = withContext(Dispatchers.IO) {
//                service.removeCartItem(item)
                delay(100)
                cartData.remove(item)
            }
            cartData = data

            _result.value = Result.Success(data)
        } catch (e: Exception) {
            _result.value = Result.Error(e)
        }
    }
}