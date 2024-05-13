package dev.felipejoglar.klimate.currentweather.data.remote.model

import dev.felipejoglar.klimate.currentweather.domain.model.CurrentWeather
import dev.felipejoglar.klimate.currentweather.domain.model.Location
import dev.felipejoglar.klimate.currentweather.domain.model.Place
import dev.felipejoglar.klimate.currentweather.domain.model.WeatherCondition
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

internal fun RemoteCurrentWeather.toModel(): CurrentWeather {
    return CurrentWeather(
        place = Place(
            location = Location(
                latitude = latitude,
                longitude = longitude,
            ),
            address = null,
        ),
        time = current.time.toLocalDateTime(),
        weatherCondition = WeatherCondition.fromCode(current.weatherCode),
        temperature = current.temperature,
        apparentTemperature = current.apparentTemperature,
        isDay = current.isDay == 1,
        sunrise = daily.sunrise.first().toLocalDateTime(),
        sunset = daily.sunset.first().toLocalDateTime(),
        precipitationProbability = hourly.precipitationProbability.first(),
        humidity = current.relativeHumidity,
        windSpeed = current.windSpeed,
        windDirection = current.windDirection,
        hourlyForecast = hourly.toForecast(),
        dailyForecast = daily.toForecast(),
    )
}

private fun RemoteCurrentWeather.Hourly.toForecast(): List<CurrentWeather.HourForecast> {
    return time.mapIndexed { index, time ->
        CurrentWeather.HourForecast(
            time = time.toLocalDateTime(),
            weatherCondition = WeatherCondition.fromCode(weatherCode[index]),
            temperature = temperature[index],
            precipitationProbability = precipitationProbability[index],
            isDay = isDay[index] == 1,
        )
    }
}

private fun RemoteCurrentWeather.Daily.toForecast(): List<CurrentWeather.DayForecast> {
    return time.mapIndexed { index, time ->
        CurrentWeather.DayForecast(
            date = time.toLocalDate(),
            weatherCondition = WeatherCondition.fromCode(weatherCode[index]),
            maxTemperature = temperatureMax[index],
            minTemperature = temperatureMin[index],
            sunrise = sunrise[index].toLocalDateTime(),
            sunset = sunset[index].toLocalDateTime(),
            precipitationProbability = precipitationProbability[index],
        )
    }
}

private fun String.toLocalDateTime() = LocalDateTime.parse(this)
private fun String.toLocalDate() = LocalDate.parse(this)
