package com.snoy.composeshopper.catalog.repo

import com.snoy.composeshopper.catalog.models.Catalog
import com.snoy.composeshopper.catalog.models.Item
import com.snoy.composeshopper.common.repo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

object FakeCatalogRepo : CatalogRepo {
    private var catalogData: Catalog? = null
    @Suppress("ObjectPropertyName")
    private val _result = MutableStateFlow<Result<Catalog>>(Result.Loading)
    private val result: StateFlow<Result<Catalog>> = _result

    private fun getFakeList(): List<Item> {
        val itemNames = listOf(
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
        return itemNames.map { Item(id = it.last().uppercaseChar().code - 'A'.code, name = it) }
    }

    override fun observeCatalog(): StateFlow<Result<Catalog>> {
        return result
    }

    override suspend fun loadCatalog() {
        _result.value = Result.Loading

        if (catalogData != null) {
            _result.value = Result.Success(catalogData!!)
            return
        }

        try {
            val data = withContext(Dispatchers.IO) {
//                service.loadCatalog()
                delay(500)
                Catalog(getFakeList())
            }
            catalogData = data

            _result.value = Result.Success(data)
        } catch (e: Exception) {
            _result.value = Result.Error(e)
        }
    }
}