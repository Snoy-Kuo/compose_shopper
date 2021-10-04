package com.snoy.composeshopper.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snoy.composeshopper.cart.repo.CartRepo
import com.snoy.composeshopper.catalog.models.Item
import com.snoy.composeshopper.catalog.repo.CatalogRepo
import kotlinx.coroutines.launch


class CatalogViewModel(
    private val cartRepo: CartRepo,
    private val catalogRepo: CatalogRepo
) : ViewModel() {

    //state
    val catalogState = catalogRepo.observeCatalog()
    val cartState = cartRepo.observeCart()

    init {
        loadCatalog()
        loadCart()
    }

    //event        
    private fun loadCatalog() {
        viewModelScope.launch {
            catalogRepo.loadCatalog()
        }
    }

    //event
    private fun loadCart() {
        viewModelScope.launch {
            cartRepo.loadCart()
        }
    }

    //event
    fun addCartItem(item: Item) {
        viewModelScope.launch {
            cartRepo.addCartItem(item)
        }
    }
}