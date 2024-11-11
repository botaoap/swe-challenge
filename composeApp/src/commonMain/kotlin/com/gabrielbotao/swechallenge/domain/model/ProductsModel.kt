package com.gabrielbotao.swechallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductsModel(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val thumbnail: String
)

data class CategoryGroup(
    val title: String,
    val item: List<ProductsModel>
)