package com.widhin.jetmovie.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.widhin.jetmovie.data.MovieRepository
import com.widhin.jetmovie.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderMovie()
                .collect { orderMovie ->
                    val total =
                        orderMovie.sumOf { it.movie.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderMovie, total))
                }
        }
    }

    fun updateOrderMovie(movieId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderMovie(movieId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderMovies()
                    }
                }
        }
    }
}