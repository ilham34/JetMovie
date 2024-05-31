package com.widhin.jetmovie.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.widhin.jetmovie.di.Injection
import com.widhin.jetmovie.model.OrderMovie
import com.widhin.jetmovie.ui.ViewModelFactory
import com.widhin.jetmovie.ui.common.UiState
import com.widhin.jetmovie.ui.components.MovieItem
import com.widhin.jetmovie.ui.components.Search
import com.widhin.jetmovie.ui.components.SectionText

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllMovies()
            }

            is UiState.Success -> {
                Column {
                    Search(
                        query = query,
                        onQueryChange = viewModel::search,
                        modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    )
                    HomeContent(
                        orderMovie = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail
                    )
                }
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderMovie: List<OrderMovie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    Column(modifier = modifier) {
        SectionText("Watch Movies")
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(orderMovie) { data ->
                MovieItem(
                    image = data.movie.image,
                    title = data.movie.title,
                    price = data.movie.price,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.movie.id)
                    }
                )
            }
        }
    }
}