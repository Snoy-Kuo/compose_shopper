package com.snoy.composeshopper.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.snoy.composeshopper.cart.repo.FakeCartRepo

class CartViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = FakeCartRepo

        @Suppress("UNCHECKED_CAST")
        return CartViewModel(
            repo = repository
        ) as T
    }
}