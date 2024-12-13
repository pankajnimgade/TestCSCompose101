package com.example.csvcompose101.network

import com.example.csvcompose101.data.models.ImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/***
 * Eg: https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=porcupine
 */
interface PhotoPublicAPI {

    @GET("services/feeds/photos_public.gne")
    fun getPictureOf(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: String = "1",
        @Query("tags") tags: String = "porcupine"
    ): Call<ImageResponse>

}
