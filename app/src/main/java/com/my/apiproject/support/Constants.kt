package com.my.apiproject.support

import com.my.apiproject.R

object Constants {

    //Link
    const val baseUrl = "https://api.github.com/"
    var pageCount = 0

    val languageColorCodes = hashMapOf(
        Pair("Java", R.color.lan_java),
        Pair("Kotlin", R.color.lan_kotlin),
        Pair("Dart", R.color.lan_dart),
        Pair("Swift", R.color.lan_swift),
        Pair("Python", R.color.lan_python),
        Pair("JavaScript", R.color.lan_python),
        Pair("Ruby", R.color.lan_python)
    )

    const val noInternetCon = "Please check your internet connection"
    const val somethingWrong = "Something Went Wrong"

}