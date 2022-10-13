package com.bikcodeh.melichallenge.util.extension

import java.util.Base64

/**
 * Extension function to encode a string in base 64
 * @return String
 */
fun String.encode(): String {
    return Base64.getEncoder().encodeToString(this.toByteArray())
}

/**
 * Extension function to decode a base 64 type into a string
 * @return String
 */
fun String.decode(): String {
    val data = Base64.getDecoder().decode(this)
    return String(data)
}