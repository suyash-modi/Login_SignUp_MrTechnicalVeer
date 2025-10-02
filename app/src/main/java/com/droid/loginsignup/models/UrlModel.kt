package com.droid.loginsignup.models

data class UnsplashResponse(val results: List<UnsplashPhoto>)
data class UnsplashPhoto(val urls: Urls)
data class Urls(val regular: String)
