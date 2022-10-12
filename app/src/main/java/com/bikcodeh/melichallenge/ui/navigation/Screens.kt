package com.bikcodeh.melichallenge.ui.navigation

import com.bikcodeh.melichallenge.data.util.Util.toJson
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.util.extension.encode

sealed class Screens(val route: String) {
    object Home : Screens("home_screen")
    object Splash : Screens("splash_screen")
    object Detail : Screens("detail_screen/{item}") {
        const val NAV_ARG_KEY = "item"
        fun passItem(item: Product): String {
            return "detail_screen/${toJson<Product>(item).encode()}"
        }
    }
}
