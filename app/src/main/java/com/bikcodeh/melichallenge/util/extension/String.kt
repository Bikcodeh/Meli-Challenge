package com.bikcodeh.melichallenge.util.extension

import java.net.URLDecoder
import java.net.URLEncoder

fun String.encode(charset: String = "UTF-8"): String {
    return URLEncoder.encode(this, charset)
}

fun String.decode(charset: String = "UTF-8"): String {
    return URLDecoder.decode(this, charset)
}