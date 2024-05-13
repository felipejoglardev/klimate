package dev.felipejoglar.klimate.data.remote


interface WeatherApi {
    suspend fun getCurrent(latitude: Double, longitude: Double): RemoteCurrentWeather
}