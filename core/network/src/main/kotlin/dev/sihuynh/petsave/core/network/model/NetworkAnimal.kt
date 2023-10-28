package dev.sihuynh.petsave.core.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAnimal(
    @SerialName("id") val id: Long,
    @SerialName("organization_id") val organizationId: String?,
    @SerialName("url") val url: String?,
    @SerialName("type") val type: String?,
    @SerialName("species") val species: String?,
    @SerialName("breeds") val breeds: NetworkBreeds?,
    @SerialName("colors") val colors: NetworkColors?,
    @SerialName("age") val age: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("size") val size: String?,
    @SerialName("coat") val coat: String?,
    @SerialName("name") val name: String?,
    @SerialName("description") val description: String?,
    @SerialName("photos") val photos: List<NetworkPhotoSizes>?,
    @SerialName("videos") val videos: List<NetworkVideoLink>?,
    @SerialName("status") val status: String?,
    @SerialName("attributes") val attributes: NetworkAttributes?,
    @SerialName("environment") val environment: NetworkEnvironment?,
    @SerialName("tags") val tags: List<String?>?,
    @SerialName("contact") val contact: NetworkContact?,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("distance") val distance: Float
)

@Serializable
data class NetworkBreeds(
    @SerialName("primary") val primary: String?,
    @SerialName("secondary") val secondary: String?,
    @SerialName("mixed") val mixed: Boolean?,
    @SerialName("unknown") val unknown: Boolean?
)

@Serializable
data class NetworkColors(
    @SerialName("primary") val primary: String?,
    @SerialName("secondary") val secondary: String?,
    @SerialName("tertiary") val tertiary: String?
)

@Serializable
data class NetworkPhotoSizes(
    @SerialName("small") val small: String?,
    @SerialName("medium") val medium: String?,
    @SerialName("large") val large: String?,
    @SerialName("full") val full: String?,
)

@Serializable
data class NetworkVideoLink(
    @SerialName("embed") val embed: String?
)

@Serializable
data class NetworkAttributes(
    @SerialName("spayed_neutered") val spayedNeutered: Boolean?,
    @SerialName("house_trained") val houseTrained: Boolean?,
    @SerialName("declawed") val declawed: Boolean?,
    @SerialName("special_needs") val specialNeeds: Boolean?,
    @SerialName("shots_current") val shotsCurrent: Boolean?,
)

@Serializable
data class NetworkEnvironment(
    @SerialName("children") val children: Boolean?,
    @SerialName("dogs") val dogs: Boolean?,
    @SerialName("cats") val cats: Boolean?,
)

@Serializable
data class NetworkContact(
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String?,
    @SerialName("address") val address: NetworkAddress?
)

@Serializable
data class NetworkAddress(
    @SerialName("address1") val address1: String?,
    @SerialName("address2") val address2: String?,
    @SerialName("city") val city: String?,
    @SerialName("state") val state: String?,
    @SerialName("postcode") val postcode: String?,
    @SerialName("country") val country: String?
)

fun Instant.Companion.parseWithBasicOffset(string: String): Instant {
    var lastDigit = string.length
    while (lastDigit > 0 && string[lastDigit - 1].isDigit()) { --lastDigit }
    val digits = string.length - lastDigit // how many digits are there at the end of the string
    if (digits <= 2)
        return parse(string) // no issue in any case
    var newString = string.substring(0, lastDigit + 2)
    lastDigit += 2
    while (lastDigit < string.length) {
        newString += ":" + string.substring(lastDigit, lastDigit + 2)
        lastDigit += 2
    }
    return parse(newString)
}