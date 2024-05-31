package com.widhin.jetmovie.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.widhin.jetmovie.data.MovieRepository
import com.widhin.jetmovie.model.Movie
import com.widhin.jetmovie.model.OrderMovie
import com.widhin.jetmovie.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderMovie>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderMovie>>
        get() = _uiState

    fun getMovieById(movieId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderMovieById(movieId))
        }
    }

    fun addToCart(movie: Movie, count: Int) {
        viewModelScope.launch {
            repository.updateOrderMovie(movie.id, count)
        }
    }
}