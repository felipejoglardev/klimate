package dev.felipejoglar.klimate.currentweather.data.remote

import dev.felipejoglar.klimate.currentweather.data.InvalidDataException
import dev.felipejoglar.klimate.currentweather.data.remote.model.toModel
import dev.felipejoglar.klimate.currentweather.domain.model.CurrentWeather

internal class RemoteWeatherDataSource(
    private val weatherApi: WeatherApi,
) {
    suspend fun fetchCurrentWeather(latitude: Double, longitude: Double): CurrentWeather {
        return try {
            weatherApi.getCurrent(latitude, longitude).toModel()
        } catch (exception: Exception) {
            throw when (exception) {
                is IllegalArgumentException,
                is IndexOutOfBoundsException -> InvalidDataException(exception.message)

                else -> exception
            }
        }
    }
}
