package com.example.csvcompose101.use.cases

import android.util.Log
import com.example.csvcompose101.data.models.ImageResponse
import com.example.csvcompose101.network.PhotoPublicAPI
import com.example.csvcompose101.network.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetPhotoUseCase {

    var TAG: String? = null

    init {
        TAG = this.javaClass.simpleName
    }

    fun getPhotos(
        text: String,
        callbackSuccess: (ImageResponse?) -> Unit,
        callbackFailure: (Throwable) -> Unit
    ) {
        val photoPublicAPI = getRetrofit().create(PhotoPublicAPI::class.java)
        photoPublicAPI.getPictureOf(tags = text).enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                response.body()?.let {

                    Log.d(TAG, "success: $it")
                    callbackSuccess(it)
                } ?: run {
                    Log.d(TAG, "success: with no results")
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                // Good luck
                Log.d(TAG, "Error ${t}")
                Log.d(TAG, "Good luck :( didn't get list")
                callbackFailure(t)
            }

        })
    }

}
