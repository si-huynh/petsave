package dev.sihuynh.petsave.core.network.fake

import dev.sihuynh.petsave.core.network.model.NetworkAddress
import dev.sihuynh.petsave.core.network.model.NetworkAnimal
import dev.sihuynh.petsave.core.network.model.NetworkAttributes
import dev.sihuynh.petsave.core.network.model.NetworkBreeds
import dev.sihuynh.petsave.core.network.model.NetworkColors
import dev.sihuynh.petsave.core.network.model.NetworkContact
import dev.sihuynh.petsave.core.network.model.NetworkEnvironment

object FakeAnimal {
    val dog = NetworkAnimal(
        id = 68763542,
        organizationId = "CA1287",
        url = "https://www.petfinder.com/dog/yahtzee-11055-68763542/ca/san-francisco/muttville-senior-dog-rescue-ca1287/?referrer_id=9f0ef590-c82e-4e46-930e-aa52a21d6a26&utm_source=api&utm_medium=partnership&utm_content=9f0ef590-c82e-4e46-930e-aa52a21d6a26",
        type = "Dog",
        species = "Dog",
        breeds = NetworkBreeds(
            primary = "Shih Tzu",
            secondary = null,
            mixed = false,
            unknown = false
        ),
        colors = NetworkColors(
            primary = "White / Cream",
            secondary = "Black",
            tertiary = null,
        ),
        age = "Senior",
        gender = "Male",
        size = "Small",
        coat = null,
        attributes = NetworkAttributes(
            spayedNeutered = true,
            houseTrained = false,
            declawed = null,
            specialNeeds = false,
            shotsCurrent = true
        ),
        environment = NetworkEnvironment(
            children = false,
            dogs = null,
            cats = null
        ),
        tags = listOf(
            "Adult Only Home Preferred",
            "Kid Compatibility No Kids",
            "Physical Stamina enjoys short walks",
            "Stairs Maybe"
        ),
        name = "Yahtzee 11055",
        description = "It&amp;#39;s Yahtzee! An adorable black and white Shih Tzu who&amp;#39;s ready to roll into your heart! Just like his namesake,...",
        photos = emptyList(),
        videos = emptyList(),
        status = "adoptable",
        publishedAt = "2023-09-07T05:55:13+0000",
        distance = null,
        contact = NetworkContact(
            email = "adoptions@muttville.org",
            phone = "(415) 272-4172",
            address = NetworkAddress(
                address1 = "255 Alabama St",
                address2 = null,
                city = "San Francisco",
                state = "CA",
                postcode = "94141",
                country = "US"
            )
        )
    )
}