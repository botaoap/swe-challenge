@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.ui.home.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielbotao.swechallenge.components.ErrorComponent
import com.gabrielbotao.swechallenge.components.ImageComponent
import com.gabrielbotao.swechallenge.components.LoadingComponent
import com.gabrielbotao.swechallenge.domain.model.CategoryGroup
import com.gabrielbotao.swechallenge.ui.home.uistate.ProductsUIState
import com.gabrielbotao.swechallenge.ui.home.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val dataState = viewModel.productsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        dataState.value.let { state ->
            when (state) {
                ProductsUIState.Loading -> LoadingComponent(modifier)

                is ProductsUIState.Success -> {
                    CategorizedLazeColumn(state.categories)
                }

                ProductsUIState.Error -> ErrorComponent(
                    errorMessage = state.toString(),
                    modifier = modifier
                ) { viewModel.getProducts() }
            }
        }
    }
}

@Composable
fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Gray)
            .padding(16.dp)
    )
}

@Composable
fun CategoryItem(
    text: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageComponent(imageUrl)
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategorizedLazeColumn(
    categories: List<CategoryGroup>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        categories.forEach { category ->
            stickyHeader {
                CategoryHeader(category.title)
            }
            items(category.item) { list ->
                CategoryItem(list.title, list.thumbnail)
            }
        }
    }
}