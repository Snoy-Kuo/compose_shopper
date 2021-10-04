package com.snoy.composeshopper.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.snoy.composeshopper.cart.repo.FakeCartRepo
import com.snoy.composeshopper.catalog.repo.FakeCatalogRepo

class CatalogViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val catalogRepo = FakeCatalogRepo
        val cartRepo = FakeCartRepo

        @Suppress("UNCHECKED_CAST")
        return CatalogViewModel(
            catalogRepo = catalogRepo,
            cartRepo = cartRepo
        ) as T
    }
}