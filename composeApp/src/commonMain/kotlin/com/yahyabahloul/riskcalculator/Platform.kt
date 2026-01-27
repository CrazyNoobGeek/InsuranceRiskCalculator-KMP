package com.yahyabahloul.riskcalculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform