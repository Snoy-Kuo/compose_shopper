package com.snoy.composeshopper.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snoy.composeshopper.cart.repo.CartRepo
import com.snoy.composeshopper.catalog.models.Item
import kotlinx.coroutines.launch


class CartViewModel(
    private val repo: CartRepo
) : ViewModel() {

    //state
    val state = repo.observeCart()

    init {
        loadCart()
    }

    //event        
    fun loadCart() {
        viewModelScope.launch {
            repo.loadCart()
        }
    }

    //event
    fun removeCartItem(item: Item) {
        viewModelScope.launch {
            repo.removeCartItem(item)
        }
    }
}