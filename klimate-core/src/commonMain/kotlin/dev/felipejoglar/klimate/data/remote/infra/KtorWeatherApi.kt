package dev.felipejoglar.klimate.data.remote.infra

import dev.felipejoglar.klimate.data.ConnectivityException
import dev.felipejoglar.klimate.data.InvalidDataException
import dev.felipejoglar.klimate.data.remote.RemoteCurrentWeather
import dev.felipejoglar.klimate.data.remote.WeatherApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

class KtorWeatherApi(
    baseUrl: String,
    private val client: HttpClient,
) : WeatherApi {

    private val baseUrlBuilder: URLBuilder = URLBuilder(baseUrl)
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getCurrent(latitude: Double, longitude: Double): RemoteCurrentWeather {
        val currentWeatherUrl = makeCurrentWeatherUrl(latitude, longitude)

        val response = try {
            client.get(currentWeatherUrl)
        } catch (e: Exception) {
            println(e)
            throw ConnectivityException()
        }

        if (!response.status.isSuccess())
            throw InvalidDataException("Not successful HTTP response. Status code: ${response.status}")

        return try {
            json.decodeFromString<RemoteCurrentWeather>(response.bodyAsText())
        } catch (e: Throwable) {
            println(e.message.orEmpty())
            throw InvalidDataException(e.message)
        }
    }

    private fun makeCurrentWeatherUrl(latitude: Double, longitude: Double): Url {
        return baseUrlBuilder.apply {
            appendPathSegments("v1", "forecast")

            parameters.append("latitude", latitude.toString())
            parameters.append("longitude", longitude.toString())

            parameters.appendCurrentWeatherQuery()
            parameters.appendHourlyForecastQuery()
            parameters.appendDailyForecastQuery()
        }.build()
    }

    private fun ParametersBuilder.appendCurrentWeatherQuery() {
        append("current", currentWeatherParams.toParamsList())
    }

    private fun ParametersBuilder.appendHourlyForecastQuery() {
        append("forecast_hours", "25")
        append("hourly", hourlyForecastParams.toParamsList())
    }

    private fun ParametersBuilder.appendDailyForecastQuery() {
        append("forecast_days", "10")
        append("daily", dailyForecastParams.toParamsList())
    }

    private fun List<String>.toParamsList(): String = joinToString(separator = ",")

    companion object {
        private val currentWeatherParams = listOf(
            "weather_code",
            "temperature_2m",
            "apparent_temperature",
            "relative_humidity_2m",
            "wind_speed_10m",
            "wind_direction_10m",
            "is_day",
        )

        private val hourlyForecastParams = listOf(
            "temperature_2m",
            "weather_code",
            "precipitation_probability",
            "is_day",
        )

        private val dailyForecastParams = listOf(
            "weather_code",
            "temperature_2m_max",
            "temperature_2m_min",
            "sunrise",
            "sunset",
            "precipitation_probability_max",
        )
    }
}