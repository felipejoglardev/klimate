package dev.felipejoglar.klimate.currentweather.domain.model

import kotlin.test.Test
import kotlin.test.assertFails

class LocationTest {

    @Test
    fun init_failsOnInvalidLatitude() {
        assertFails { makeLocation(latitude = -90.0001) }
        assertFails { makeLocation(latitude = 90.0001) }
    }

    @Test
    fun init_failsOnInvalidLongitude() {
        assertFails { makeLocation(longitude = -180.0001) }
        assertFails { makeLocation(longitude = 180.0001) }
    }

    // region Helpers

    private fun makeLocation(
        latitude: Double = 0.0,
        longitude: Double = 0.0,
    ): Location {
        return Location(latitude, longitude)
    }

    // endregion
}
