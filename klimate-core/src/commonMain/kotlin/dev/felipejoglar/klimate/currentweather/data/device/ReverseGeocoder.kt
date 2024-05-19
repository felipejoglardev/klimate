package dev.felipejoglar.klimate.currentweather.data.device

import dev.felipejoglar.klimate.currentweather.domain.model.Address

internal interface ReverseGeocoder {
    suspend fun fromLocation(latitude: Double, longitude: Double): Address?
}
