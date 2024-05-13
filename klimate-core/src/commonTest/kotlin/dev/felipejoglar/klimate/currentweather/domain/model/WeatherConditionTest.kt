package dev.felipejoglar.klimate.currentweather.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class WeatherConditionTest {

    @Test
    fun fromCode_failsOnInvalidCode() {
        assertFails { WeatherCondition.fromCode(999) }
        assertFails { WeatherCondition.fromCode(-1) }
    }

    @Test
    fun fromCode_returnsCorrectCondition() {
        WeatherCondition.entries.forEach {
            assertEquals(it, WeatherCondition.fromCode(it.code))
        }
    }
}