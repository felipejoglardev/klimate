package dev.felipejoglar.klimate.di

import dev.felipejoglar.klimate.currentweather.data.device.ReverseGeocoder

internal expect class KlimateFactory {
    fun createReverseGeocoder(): ReverseGeocoder
}
