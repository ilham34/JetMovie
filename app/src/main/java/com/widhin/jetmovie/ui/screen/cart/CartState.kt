package com.widhin.jetmovie.ui.screen.cart

import com.widhin.jetmovie.model.OrderMovie

data class CartState(
    val orderMovie: List<OrderMovie>,
    val total: Int
)