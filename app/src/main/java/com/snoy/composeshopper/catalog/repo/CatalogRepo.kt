package com.snoy.composeshopper.catalog.repo

import com.snoy.composeshopper.catalog.models.Catalog
import com.snoy.composeshopper.common.repo.Result
import kotlinx.coroutines.flow.StateFlow

interface CatalogRepo {
    fun observeCatalog(): StateFlow<Result<Catalog>>
    suspend fun loadCatalog()
}