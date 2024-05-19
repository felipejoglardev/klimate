package dev.felipejoglar.klimate.currentweather.domain.model

internal data class Address(
    val locality: String,
    val region: String?,
    val country: String,
)