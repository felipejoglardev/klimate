package dev.felipejoglar.klimate.di

import dev.felipejoglar.klimate.currentweather.data.device.CoreLocationReverseGeocoder
import dev.felipejoglar.klimate.currentweather.data.device.ReverseGeocoder

internal actual class KlimateFactory {
    actual fun createReverseGeocoder(): ReverseGeocoder {
        return CoreLocationReverseGeocoder()
    }
}
