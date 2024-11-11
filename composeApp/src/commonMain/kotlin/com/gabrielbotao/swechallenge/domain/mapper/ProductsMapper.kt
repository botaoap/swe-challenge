package com.gabrielbotao.swechallenge.domain.mapper

import com.gabrielbotao.swechallenge.data.remote.response.ProductsResponse
import com.gabrielbotao.swechallenge.domain.model.ProductsModel

class ProductsMapper {

    fun getProducts(
        response: ProductsResponse
    ) = response.products.map {
        ProductsModel(
            id = it.id,
            title = it.title,
            description = it.description,
            category = it.category,
            price = it.price,
            discountPercentage = it.discountPercentage,
            thumbnail = it.thumbnail
        )
    }
}