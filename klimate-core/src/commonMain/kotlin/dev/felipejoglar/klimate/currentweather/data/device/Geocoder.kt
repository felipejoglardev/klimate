package dev.felipejoglar.klimate.currentweather.data.device

import dev.felipejoglar.klimate.currentweather.domain.model.Address
import dev.felipejoglar.klimate.currentweather.domain.model.Location

internal class Geocoder(
    private val geocoder: ReverseGeocoder,
) {
    suspend fun fromLocation(location: Location): Address? {
        return geocoder.fromLocation(location.latitude, location.longitude)
    }
}


