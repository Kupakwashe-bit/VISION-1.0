package com.krimson.kuona.navigation

sealed class Screen(val route: String) {
    data object Boot : Screen("boot")
    data object Scan : Screen("scan")
    data object Analyst : Screen("analyst")
    data object Devices : Screen("devices")
    data object Reports : Screen("reports")
    data object Settings : Screen("settings")
}
