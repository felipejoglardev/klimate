package dev.felipejoglar.klimate.currentweather.domain

import dev.felipejoglar.klimate.currentweather.domain.model.CurrentWeather
import dev.felipejoglar.klimate.currentweather.domain.model.Location
import kotlinx.coroutines.flow.Flow

internal interface CurrentWeatherLoader {
    fun load(location: Location): Flow<CurrentWeather>
}