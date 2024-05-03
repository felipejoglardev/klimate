package dev.felipejoglar.klimate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform