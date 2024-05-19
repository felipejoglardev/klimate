package dev.felipejoglar.klimate.di

import android.content.Context
import dev.felipejoglar.klimate.currentweather.data.device.AndroidReverseGeocoder
import dev.felipejoglar.klimate.currentweather.data.device.ReverseGeocoder

internal actual class KlimateFactory(
    private val context: Context,
) {
    actual fun createReverseGeocoder(): ReverseGeocoder {
        return AndroidReverseGeocoder(context)
    }
}
