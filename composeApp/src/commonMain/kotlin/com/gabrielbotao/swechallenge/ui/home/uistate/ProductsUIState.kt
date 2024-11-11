package com.gabrielbotao.swechallenge.ui.home.uistate

import com.gabrielbotao.swechallenge.domain.model.CategoryGroup
import com.gabrielbotao.swechallenge.domain.model.ProductsModel

sealed class ProductsUIState {
    data object Loading: ProductsUIState()
    data class Success(
        val data: List<ProductsModel>,
        val categories: List<CategoryGroup>
    ): ProductsUIState()
    data object Error: ProductsUIState()
}