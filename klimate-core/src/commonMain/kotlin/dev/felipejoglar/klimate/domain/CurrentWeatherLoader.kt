package dev.felipejoglar.klimate.domain

import dev.felipejoglar.klimate.domain.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherLoader {
    fun load(latitude: Double, longitude: Double): Flow<CurrentWeather>
}