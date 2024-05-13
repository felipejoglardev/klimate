package dev.felipejoglar.klimate.currentweather.data

import dev.felipejoglar.klimate.currentweather.data.remote.RemoteWeatherDataSource
import dev.felipejoglar.klimate.currentweather.data.remote.infra.KtorWeatherApi
import dev.felipejoglar.klimate.currentweather.domain.model.Location
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class WeatherRepositoryTest {

    private val httpEngine = mockEngine()
    private val remoteDataSource = createRemoteDataSource(httpEngine)

    private val sut = WeatherRepository(remoteDataSource)

    @Test
    fun load_setsRequestUrlCorrectly() = runTest {
        sut.load(location).collect()

        val request = httpEngine.requestHistory.first()
        assertEquals(HttpMethod.Get, request.method, "Wrong request HTTP method")

        val url = httpEngine.requestHistory.first().url
        assertEquals(listOf("v1", "forecast"), url.nonEmptyPathSegments(), "Wrong path segments")
        assertEquals(latitude.toString(), url.parameters["latitude"], "Wrong latitude")
        assertEquals(longitude.toString(), url.parameters["longitude"], "Wrong longitude")

        assertParams(currentWeatherParams, url.parameters["current"].toList(), "Current Weather")

        assertEquals("25", url.parameters["forecast_hours"], "Wrong hourly count")
        assertParams(hourlyForecastParams, url.parameters["hourly"].toList(), "Hourly Forecast")

        assertEquals("10", url.parameters["forecast_days"], "Wrong daily count")
        assertParams(dailyForecastParams, url.parameters["daily"].toList(), "Daily Forecast")
    }

    @Test
    fun load_returnsCurrentWeather_onValidResponse() = runTest {
        httpEngine.completeSuccessfully(validResponseJson())

        val result = sut.load(location).first()

        assertEquals(validResponseModel(), result)
    }

    @Test
    fun load_throwsConnectivityException_onRequestFailure() = runTest {
        httpEngine.completeWithFailure()

        assertFailsWith(ConnectivityException::class) {
            sut.load(location).collect()
        }
    }

    @Test
    fun load_throwsInvalidDataException_onInvalidResponse() = runTest {
        httpEngine.completeSuccessfully("invalid json")

        assertFailsWith(InvalidDataException::class) {
            sut.load(location).collect()
        }
    }

    @Test
    fun load_throwsInvalidDataException_onNon2XXResponse() = runTest {
        listOf(199, 300, 400, 500).forEach { code ->
            httpEngine.completeSuccessfullyWithStatusCode(code)

            assertFailsWith(InvalidDataException::class) {
                sut.load(location).collect()
            }
        }
    }

    @Test
    fun load_throwsInvalidDataException_onMappingError() = runTest {
        val invalidLatitude = 90.001
        val invalidLongitude = 180.001

        httpEngine.completeSuccessfully(validResponseJson(latitude = invalidLatitude))
        assertFailsWith(InvalidDataException::class, "Invalid latitude") {
            sut.load(location).collect()
        }

        httpEngine.completeSuccessfully(validResponseJson(longitude = invalidLongitude))
        assertFailsWith(InvalidDataException::class, "Invalid longitude") {
            sut.load(location).collect()
        }

        httpEngine.completeSuccessfully(invalidResponseJson())
        assertFailsWith(InvalidDataException::class, "Invalid forecasts count") {
            sut.load(location).collect()
        }
    }

    // region Helpers

    private fun createRemoteDataSource(engine: MockEngine): RemoteWeatherDataSource {
        val url = "https://a-url.com"
        val client = HttpClient(engine)
        val weatherApi = KtorWeatherApi(url, client)

        return RemoteWeatherDataSource(weatherApi)
    }

    private fun mockEngine() = MockEngine { respondOk(validResponseJson()) }

    private val latitude = 12.4567
    private val longitude = -9.8765
    private val location = Location(latitude, longitude)

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

    private fun assertParams(
        expected: List<String>,
        actual: List<String>,
        message: String,
    ) {
        assertTrue(
            actual.duplicates().isEmpty(),
            "$message. Duplicated params: ${actual.duplicates()}"
        )
        assertEquals(expected.toSet(), actual.toSet(), "$message. Wrong params")
    }

    private fun Url.nonEmptyPathSegments() = pathSegments.filter { it.isNotEmpty() }
    private fun String?.toList() = orEmpty().split(",")
    private fun List<String>.duplicates() = groupBy { it }.filter { it.value.size > 1 }.keys

    private fun MockEngine.completeSuccessfully(jsonString: String) {
        config.requestHandlers.clear()
        config.requestHandlers.add { respondOk(jsonString) }
    }

    private fun MockEngine.completeSuccessfullyWithStatusCode(code: Int) {
        config.requestHandlers.clear()
        config.requestHandlers.add { respond(validResponseJson(), HttpStatusCode.fromValue(code)) }
    }

    private fun MockEngine.completeWithFailure() {
        config.requestHandlers.clear()
        config.requestHandlers.add { throw Exception() }
    }

    // endregion
}
