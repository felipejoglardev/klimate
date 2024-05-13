package dev.felipejoglar.klimate.domain.model

data class Location(
    val latitude: Double,
    val longitude: Double,
) {
    init {
        require(latitude in -90.0..90.0) { "Latitude must be in range of -90 to 90°. Given: $latitude." }
        require(longitude in -180.0..180.0) { "Longitude must be in range -180.0 and 180.0. Given: $longitude" }
    }
}
