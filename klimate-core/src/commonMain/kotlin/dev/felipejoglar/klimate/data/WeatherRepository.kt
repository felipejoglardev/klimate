package dev.felipejoglar.klimate.data

import dev.felipejoglar.klimate.domain.model.CurrentWeather
import dev.felipejoglar.klimate.domain.CurrentWeatherLoader
import dev.felipejoglar.klimate.data.remote.RemoteWeatherDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepository(
    private val remoteDataSource: RemoteWeatherDataSource,
) : CurrentWeatherLoader {

    override fun load(latitude: Double, longitude: Double): Flow<CurrentWeather> {
        return flow {
            emit(remoteDataSource.fetchCurrentWeather(latitude, longitude))
        }
    }
}

class ConnectivityException : Exception()
class InvalidDataException(message: String?) : Exception(message)
