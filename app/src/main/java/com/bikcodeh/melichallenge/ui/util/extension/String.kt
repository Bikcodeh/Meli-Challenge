package com.bikcodeh.melichallenge.ui.util.extension

fun String.fixHttp(): String {
    return this.replace("http", "https")
}