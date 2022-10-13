package com.bikcodeh.melichallenge.util.extension

import java.util.Base64

fun String.encode(): String {
    return Base64.getEncoder().encodeToString(this.toByteArray())
}

fun String.decode(): String {
    val data = Base64.getDecoder().decode(this)
    return String(data)
}