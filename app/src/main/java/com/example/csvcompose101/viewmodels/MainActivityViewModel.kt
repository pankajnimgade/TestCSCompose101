package com.example.csvcompose101.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.csvcompose101.data.models.ImageResponse
import com.example.csvcompose101.data.models.Item
import com.example.csvcompose101.use.cases.GetPhotoUseCase

class MainActivityViewModel : ViewModel() {

    var TAG: String? = null

    init {
        TAG = this.javaClass.simpleName
    }

    var list: SnapshotStateList<Item> = mutableStateListOf<Item>()

    fun searchImageForText(text: String = "dog") {
        val useCase = GetPhotoUseCase()
        useCase.getPhotos(text = text, ::callbackSuccess, ::callbackFailure)
    }

    private fun callbackFailure(throwable: Throwable) {
        Log.d(TAG, "callbackFailure: $throwable")
        list.clear() // if new request fails clear list
    }

    private fun callbackSuccess(imageResponse: ImageResponse?) {
        Log.d(TAG, "callbackSuccess: ${imageResponse}")
        imageResponse?.let {
            if (it.items.isNotEmpty()) {
                list.clear()
                list.addAll(it.items)
            }
        }
    }

}
