package dev.felipejoglar.klimate.domain.model

data class Place(
    val location: Location,
    val address: Address?,
) {
    data class Address(
        val locality: String,
        val region: String?,
        val country: String,
    )
}
