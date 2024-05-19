package dev.felipejoglar.klimate.currentweather.data.device

import android.content.Context
import android.location.Geocoder
import android.os.Build
import dev.felipejoglar.klimate.currentweather.domain.model.Address
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import android.location.Address as AndroidAddress

internal class AndroidReverseGeocoder(
    private val context: Context,
) : ReverseGeocoder {

    override suspend fun fromLocation(latitude: Double, longitude: Double): Address? {
        val geocoder = Geocoder(context)
        val address = geocoder.getAddress(latitude, longitude)

        return address?.toModel()
    }

    private suspend fun Geocoder.getAddress(latitude: Double, longitude: Double): AndroidAddress? {
        return suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getFromLocation(latitude, longitude, 1) { addresses ->
                    continuation.resume(addresses.firstOrNull())
                }
            } else {
                @Suppress("DEPRECATION")
                val address = getFromLocation(latitude, longitude, 1)?.firstOrNull()
                continuation.resume(address)
            }
        }
    }

    private fun AndroidAddress.toModel(): Address? {
        if (locality == null || countryCode == null) return null

        return Address(
            locality = locality,
            region = subAdminArea,
            country = countryCode,
        )
    }
}