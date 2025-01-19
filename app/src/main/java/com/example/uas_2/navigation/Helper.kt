package com.example.uas_2.navigation

import com.example.uas_2.navigation.Screen

fun String?.HelperBar(): Boolean {
    return this in setOf(
        Screen.ScreenAdmin.route,
    )
}