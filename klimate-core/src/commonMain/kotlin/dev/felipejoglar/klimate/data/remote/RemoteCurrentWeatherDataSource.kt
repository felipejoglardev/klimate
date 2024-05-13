package dev.felipejoglar.klimate.data.remote

import dev.felipejoglar.klimate.data.InvalidDataException
import dev.felipejoglar.klimate.domain.model.CurrentWeather

class RemoteWeatherDataSource(
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
