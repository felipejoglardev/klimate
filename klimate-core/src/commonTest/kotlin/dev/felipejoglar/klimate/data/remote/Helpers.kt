package dev.felipejoglar.klimate.data.remote

import dev.felipejoglar.klimate.domain.model.CurrentWeather
import dev.felipejoglar.klimate.domain.model.Location
import dev.felipejoglar.klimate.domain.model.Place
import dev.felipejoglar.klimate.domain.model.WeatherCondition
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun validResponseJson(
    latitude: Double = 41.65,
    longitude: Double = -4.73,
) = """
    {
        "latitude": $latitude,
        "longitude": $longitude,
        "generationtime_ms": 0.5710124969482422,
        "utc_offset_seconds": 0,
        "timezone": "GMT",
        "timezone_abbreviation": "GMT",
        "elevation": 693.0,
        "current_units": {
            "time": "iso8601",
            "interval": "seconds",
            "temperature_2m": "°C",
            "relative_humidity_2m": "%",
            "apparent_temperature": "°C",
            "is_day": "",
            "weather_code": "wmo code",
            "wind_speed_10m": "km/h",
            "wind_direction_10m": "°"
        },
        "current": {
            "time": "2024-05-09T13:30",
            "interval": 900,
            "temperature_2m": 25.3,
            "relative_humidity_2m": 23,
            "apparent_temperature": 22.4,
            "is_day": 1,
            "weather_code": 3,
            "wind_speed_10m": 9.5,
            "wind_direction_10m": 233
        },
        "hourly_units": {
            "time": "iso8601",
            "temperature_2m": "°C",
            "precipitation_probability": "%",
            "weather_code": "wmo code",
            "is_day": ""
        },
        "hourly": {
            "time": [
                "2024-05-09T13:00",
                "2024-05-09T14:00",
                "2024-05-09T15:00",
                "2024-05-09T16:00",
                "2024-05-09T17:00"
            ],
            "temperature_2m": [
                24.9,
                25.6,
                25.9,
                25.5,
                25.0
            ],
            "precipitation_probability": [
                0,
                0,
                0,
                0,
                0
            ],
            "weather_code": [
                3,
                3,
                3,
                3,
                3
            ],
            "is_day": [
                1,
                1,
                1,
                1,
                1
            ]
        },
        "daily_units": {
            "time": "iso8601",
            "weather_code": "wmo code",
            "temperature_2m_max": "°C",
            "temperature_2m_min": "°C",
            "sunrise": "iso8601",
            "sunset": "iso8601",
            "precipitation_probability_max": "%"
        },
        "daily": {
            "time": [
                "2024-05-09",
                "2024-05-10"
            ],
            "weather_code": [
                3,
                3
            ],
            "temperature_2m_max": [
                25.9,
                28.3
            ],
            "temperature_2m_min": [
                8.1,
                12.5
            ],
            "sunrise": [
                "2024-05-09T05:05",
                "2024-05-10T05:03"
            ],
            "sunset": [
                "2024-05-09T19:25",
                "2024-05-10T19:26"
            ],
            "precipitation_probability_max": [
                0,
                28
            ]
        }
    }
""".trimIndent()

fun validResponseModel(
    latitude: Double = 41.65,
    longitude: Double = -4.73,
) = CurrentWeather(
    place = Place(
        location = Location(latitude, longitude),
        address = null,
    ),
    weatherCondition = WeatherCondition.OVERCAST,
    temperature = 25.3,
    apparentTemperature = 22.4,
    isDay = true,
    sunrise = LocalDateTime(2024, 5, 9, 5, 5),
    sunset = LocalDateTime(2024, 5, 9, 19, 25),
    precipitationProbability = 0,
    humidity = 23,
    windSpeed = 9.5,
    windDirection = 233,
    hourlyForecast = listOf(
        CurrentWeather.HourForecast(
            time = LocalDateTime(2024, 5, 9, 13, 0),
            weatherCondition = WeatherCondition.OVERCAST,
            temperature = 24.9,
            precipitationProbability = 0,
            isDay = true,
        ),
        CurrentWeather.HourForecast(
            time = LocalDateTime(2024, 5, 9, 14, 0),
            weatherCondition = WeatherCondition.OVERCAST,
            temperature = 25.6,
            precipitationProbability = 0,
            isDay = true,
        ),
        CurrentWeather.HourForecast(
            time = LocalDateTime(2024, 5, 9, 15, 0),
            weatherCondition = WeatherCondition.OVERCAST,
            temperature = 25.9,
            precipitationProbability = 0,
            isDay = true,
        ),
        CurrentWeather.HourForecast(
            time = LocalDateTime(2024, 5, 9, 16, 0),
            weatherCondition = WeatherCondition.OVERCAST,
            temperature = 25.5,
            precipitationProbability = 0,
            isDay = true,
        ),
        CurrentWeather.HourForecast(
            time = LocalDateTime(2024, 5, 9, 17, 0),
            weatherCondition = WeatherCondition.OVERCAST,
            temperature = 25.0,
            precipitationProbability = 0,
            isDay = true,
        ),
    ),
    dailyForecast = listOf(
        CurrentWeather.DayForecast(
            date = LocalDate(2024, 5, 9),
            weatherCondition = WeatherCondition.OVERCAST,
            maxTemperature = 25.9,
            minTemperature = 8.1,
            precipitationProbability = 0,
            sunrise = LocalDateTime(2024, 5, 9, 5, 5),
            sunset = LocalDateTime(2024, 5, 9, 19, 25),
        ),
        CurrentWeather.DayForecast(
            date = LocalDate(2024, 5, 10),
            weatherCondition = WeatherCondition.OVERCAST,
            maxTemperature = 28.3,
            minTemperature = 12.5,
            precipitationProbability = 28,
            sunrise = LocalDateTime(2024, 5, 10, 5, 3),
            sunset = LocalDateTime(2024, 5, 10, 19, 26),
        ),
    ),
)

fun invalidResponseJson(
    latitude: Double = 41.65,
    longitude: Double = -4.73,
) = """
    {
        "latitude": $latitude,
        "longitude": $longitude,
        "generationtime_ms": 0.5710124969482422,
        "utc_offset_seconds": 0,
        "timezone": "GMT",
        "timezone_abbreviation": "GMT",
        "elevation": 693.0,
        "current_units": {
            "time": "iso8601",
            "interval": "seconds",
            "temperature_2m": "°C",
            "relative_humidity_2m": "%",
            "apparent_temperature": "°C",
            "is_day": "",
            "weather_code": "wmo code",
            "wind_speed_10m": "km/h",
            "wind_direction_10m": "°"
        },
        "current": {
            "time": "2024-05-09T13:30",
            "interval": 900,
            "temperature_2m": 25.3,
            "relative_humidity_2m": 23,
            "apparent_temperature": 22.4,
            "is_day": 1,
            "weather_code": 3,
            "wind_speed_10m": 9.5,
            "wind_direction_10m": 233
        },
        "hourly_units": {
            "time": "iso8601",
            "temperature_2m": "°C",
            "precipitation_probability": "%",
            "weather_code": "wmo code",
            "is_day": ""
        },
        "hourly": {
            "time": [
                "2024-05-09T13:00",
                "2024-05-09T14:00",
                "2024-05-09T15:00"
            ],
            "temperature_2m": [
                24.9,
                25.6
            ],
            "precipitation_probability": [
                0,
                0,
                0
            ],
            "weather_code": [
                3,
                3,
                3
            ],
            "is_day": [
                1,
                1,
                1
            ]
        },
        "daily_units": {
            "time": "iso8601",
            "weather_code": "wmo code",
            "temperature_2m_max": "°C",
            "temperature_2m_min": "°C",
            "sunrise": "iso8601",
            "sunset": "iso8601",
            "precipitation_probability_max": "%"
        },
        "daily": {
            "time": [
                "2024-05-09",
                "2024-05-10"
            ],
            "weather_code": [
                3,
                3
            ],
            "temperature_2m_max": [
                25.9,
                28.3
            ],
            "temperature_2m_min": [
                8.1,
                12.5
            ],
            "sunrise": [
                "2024-05-09T05:05",
                "2024-05-10T05:03"
            ],
            "sunset": [
                "2024-05-09T19:25",
                "2024-05-10T19:26"
            ],
            "precipitation_probability_max": [
                0,
                28
            ]
        }
    }
""".trimIndent()
