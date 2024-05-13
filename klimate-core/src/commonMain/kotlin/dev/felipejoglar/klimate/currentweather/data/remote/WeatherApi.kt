package dev.felipejoglar.klimate.currentweather.data.remote

import dev.felipejoglar.klimate.currentweather.data.remote.model.RemoteCurrentWeather

internal interface WeatherApi {
    suspend fun getCurrent(latitude: Double, longitude: Double): RemoteCurrentWeather
}
