package dev.felipejoglar.klimate.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class CurrentWeather(
    val place: Place,
    val weatherCondition: WeatherCondition,
    val temperature: Double,
    val apparentTemperature: Double,
    val isDay: Boolean,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val precipitationProbability: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val hourlyForecast: List<HourForecast>,
    val dailyForecast: List<DayForecast>,
) {
    data class HourForecast(
        val time: LocalDateTime,
        val weatherCondition: WeatherCondition,
        val temperature: Double,
        val precipitationProbability: Int,
        val isDay: Boolean,
    )

    data class DayForecast(
        val date: LocalDate,
        val weatherCondition: WeatherCondition,
        val maxTemperature: Double,
        val minTemperature: Double,
        val precipitationProbability: Int,
        val sunrise: LocalDateTime,
        val sunset: LocalDateTime,
    )
}
