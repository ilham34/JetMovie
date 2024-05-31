package com.widhin.jetmovie.data

import com.widhin.jetmovie.model.FakeMovieDataSource
import com.widhin.jetmovie.model.OrderMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepository {

    private val orderMovies = mutableListOf<OrderMovie>()

    init {
        if (orderMovies.isEmpty()) {
            FakeMovieDataSource.dummyMovie.forEach {
                orderMovies.add(OrderMovie(it, 0))
            }
        }
    }

    fun getAllMovies(): Flow<List<OrderMovie>> = flowOf(orderMovies)

    fun getOrderMovieById(movieId: Long): OrderMovie =
        orderMovies.first {
            it.movie.id == movieId
        }

    fun updateOrderMovie(movieId: Long, newContentValue: Int): Flow<Boolean> {
        val index = orderMovies.indexOfFirst { it.movie.id == movieId }
        val result = if (index >= 0) {
            val orderMovie = orderMovies[index]
            orderMovies[index] = orderMovie.copy(movie = orderMovie.movie, count = newContentValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderMovie(): Flow<List<OrderMovie>> {
        return getAllMovies()
            .map { orderMovies ->
                orderMovies.filter { orderMovie ->
                    orderMovie.count != 0
                }
            }
    }

    fun searchMovies(query: String): List<OrderMovie> =
        orderMovies.filter {
            it.movie.title.contains(query, ignoreCase = true)
        }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository =
            instance ?: synchronized(this) {
                MovieRepository().apply {
                    instance = this
                }
            }
    }
}