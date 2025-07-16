package com.eliascoelho911.common

fun java.net.URI.toAndroidUri(): android.net.Uri {
    return android.net.Uri.parse(this.toString())
}

fun android.net.Uri.toJavaURI(): java.net.URI {
    return java.net.URI.create(this.toString())
}