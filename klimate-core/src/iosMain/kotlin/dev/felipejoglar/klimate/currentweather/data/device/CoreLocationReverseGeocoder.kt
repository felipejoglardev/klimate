package dev.felipejoglar.klimate.currentweather.data.device

import dev.felipejoglar.klimate.currentweather.domain.model.Address
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLPlacemark
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import kotlin.coroutines.resume

internal class CoreLocationReverseGeocoder : ReverseGeocoder {

    override suspend fun fromLocation(latitude: Double, longitude: Double): Address? {
        val geocoder = CLGeocoder()
        val place = geocoder.getPlace(latitude, longitude)

        return place?.toModel()
    }

    private suspend fun CLGeocoder.getPlace(latitude: Double, longitude: Double): CLPlacemark? {
        return suspendCancellableCoroutine { continuation ->
            val location = CLLocation(latitude, longitude)
            val locale = NSLocale.currentLocale

            reverseGeocodeLocation(location, locale) { places, error ->
                when {
                    error != null -> continuation.resume(null)
                    else -> continuation.resume(places?.firstOrNull() as? CLPlacemark)
                }
            }

            continuation.invokeOnCancellation { cancelGeocode() }
        }
    }

    private fun CLPlacemark.toModel(): Address? {
        if (locality == null || country == null) return null

        return Address(
            locality = locality!!,
            region = administrativeArea,
            country = country!!,
        )
    }
}