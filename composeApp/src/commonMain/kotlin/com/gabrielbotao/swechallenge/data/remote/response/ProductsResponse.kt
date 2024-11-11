package com.gabrielbotao.swechallenge.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val products: List<ProductsDataResponse>
)

@Serializable
data class ProductsDataResponse(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val thumbnail: String
)