package com.widhin.jetmovie.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailMovie : Screen("home/{movieId}") {
        fun createRoute(movieId: Long) = "home/$movieId"
    }
}