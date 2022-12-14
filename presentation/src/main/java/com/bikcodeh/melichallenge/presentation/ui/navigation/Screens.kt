package com.bikcodeh.melichallenge.presentation.ui.navigation

import com.bikcodeh.melichallenge.data.util.Util
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.presentation.util.extension.encode

sealed class Screens(val route: String) {
    object Home : Screens("home_screen")
    object Splash : Screens("splash_screen")
    object Detail : Screens("detail_screen/{item}") {
        const val NAV_ARG_KEY = "item"
        fun passItem(item: Product): String {
            return "detail_screen/${Util.toJson<Product>(item).encode()}"
        }
    }
}
