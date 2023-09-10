package dev.sihuynh.petsave.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.sihuynh.petsave.core.network.BuildConfig
import dev.sihuynh.petsave.core.network.PetSaveNetworkDataSource
import dev.sihuynh.petsave.core.network.model.NetworkPaginatedAnimals
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface RetrofitPetSaveNetworkApi {
    @GET(ApiConstants.ANIMALS_ENDPOINT)
    suspend fun getNearByAnimals(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int,
        @Query(ApiParameters.LOCATION) postcode: String,
        @Query(ApiParameters.DISTANCE) maxDistance: Int,
    ): NetworkPaginatedAnimals

    @GET(ApiConstants.ANIMALS_ENDPOINT)
    suspend fun getAnimalsByType(
        @Query(ApiParameters.TYPE) type: String
    ): NetworkPaginatedAnimals
}

private const val PETSAVE_BASE_URL = BuildConfig.BACKEND_URL

@Singleton
class RetrofitPetSaveNetwork @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory
) : PetSaveNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(PETSAVE_BASE_URL)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory(
                ApiConstants.JSON_CONTENT_TYPE.toMediaType()
            )
        )
        .build()
        .create(RetrofitPetSaveNetworkApi::class.java)

    override suspend fun getNearByAnimals(
        pageToLoad: Int,
        pageSize: Int,
        postcode: String,
        maxDistance: Int
    ): NetworkPaginatedAnimals = networkApi.getNearByAnimals(pageToLoad, pageSize, postcode, maxDistance)

    override suspend fun getAnimalsByType(type: String): NetworkPaginatedAnimals {
        return networkApi.getAnimalsByType(type)
    }
}