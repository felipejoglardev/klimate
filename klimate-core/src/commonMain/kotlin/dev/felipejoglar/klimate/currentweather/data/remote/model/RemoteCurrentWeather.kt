package dev.felipejoglar.klimate.currentweather.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteCurrentWeather(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("timezone") val timezone: String,
    @SerialName("current") val current: Current,
    @SerialName("hourly") val hourly: Hourly,
    @SerialName("daily") val daily: Daily,
) {
    @Serializable
    data class Current(
        @SerialName("time") val time: String,
        @SerialName("interval") val interval: Int,
        @SerialName("temperature_2m") val temperature: Double,
        @SerialName("relative_humidity_2m") val relativeHumidity: Int,
        @SerialName("apparent_temperature") val apparentTemperature: Double,
        @SerialName("is_day") val isDay: Int,
        @SerialName("weather_code") val weatherCode: Int,
        @SerialName("wind_speed_10m") val windSpeed: Double,
        @SerialName("wind_direction_10m") val windDirection: Int,
    )

    @Serializable
    data class Hourly(
        @SerialName("time") val time: List<String>,
        @SerialName("temperature_2m") val temperature: List<Double>,
        @SerialName("precipitation_probability") val precipitationProbability: List<Int>,
        @SerialName("weather_code") val weatherCode: List<Int>,
        @SerialName("is_day") val isDay: List<Int>,
    )

    @Serializable
    data class Daily(
        @SerialName("time") val time: List<String>,
        @SerialName("weather_code") val weatherCode: List<Int>,
        @SerialName("temperature_2m_max") val temperatureMax: List<Double>,
        @SerialName("temperature_2m_min") val temperatureMin: List<Double>,
        @SerialName("sunrise") val sunrise: List<String>,
        @SerialName("sunset") val sunset: List<String>,
        @SerialName("precipitation_probability_max") val precipitationProbability: List<Int>,
    )
}
