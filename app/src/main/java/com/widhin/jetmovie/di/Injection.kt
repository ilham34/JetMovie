package com.widhin.jetmovie.di

import com.widhin.jetmovie.data.MovieRepository


object Injection {
    fun provideRepository(): MovieRepository {
        return MovieRepository.getInstance()
    }
}