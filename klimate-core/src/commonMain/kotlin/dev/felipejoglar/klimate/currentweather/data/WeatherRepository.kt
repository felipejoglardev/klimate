package dev.felipejoglar.klimate.currentweather.data

import dev.felipejoglar.klimate.currentweather.data.remote.RemoteWeatherDataSource
import dev.felipejoglar.klimate.currentweather.domain.CurrentWeatherLoader
import dev.felipejoglar.klimate.currentweather.domain.model.CurrentWeather
import dev.felipejoglar.klimate.currentweather.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class WeatherRepository(
    private val remoteDataSource: RemoteWeatherDataSource,
) : CurrentWeatherLoader {

    override fun load(location: Location): Flow<CurrentWeather> {
        return flow {
            emit(remoteDataSource.fetchCurrentWeather(location.latitude, location.longitude))
        }
    }
}

internal class ConnectivityException : Exception()
internal class InvalidDataException(message: String?) : Exception(message)
