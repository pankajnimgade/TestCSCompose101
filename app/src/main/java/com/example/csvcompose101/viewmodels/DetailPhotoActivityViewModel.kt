package com.example.csvcompose101.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.csvcompose101.data.models.Item
import com.example.csvcompose101.utilities.changeDateFrom
import com.fleeksoft.ksoup.Ksoup
import com.google.gson.Gson

class DetailPhotoActivityViewModel : ViewModel() {

    var TAG: String? = null

    init {
        TAG = this.javaClass.simpleName
    }

    var item = mutableStateOf(Item())

    fun initializeItem(jsonText: String?) {
        jsonText?.let {
            item.value = Gson().fromJson(it, Item::class.java)
        }
    }

    fun getDescription(): String {
        var description: String = "Missing description"
        item?.let {
            if (item?.value?.description != null) {
                val document = Ksoup.parse(item.value.description)
                document.body()
                Log.d(TAG, "getDescription: ${document.body()}")
                for (index in document.body()._childNodes.size - 1 downTo 0) {
                    if (!document.body()._childNodes[index].toString().isNullOrEmpty()) {
                        description = document.body()._childNodes[index]
                            .toString()
                            .replace("<p>", "")
                            .replace("</p>", "")
                        break
                    }
                }
                Log.d(TAG, "getDescription: ${document.body()._childNodes.first()}")
                Log.d(TAG, "getDescription: ${document}")
            }
        }
        return description
    }


    fun getAuthor(): String {
        var author: String = "NA"
        item?.let {
            if (item?.value?.author != null) {
                var currentText = item?.value?.author
                currentText =
                    currentText?.substring(currentText.lastIndexOf("(\"") + 1)
                        ?.replace("\")", "")
                        ?.replace("\"", "")
                author = currentText ?: "NA"
            }
        }
        return author
    }

    fun getFormattedDate(): String {
        var date: String = "NA"
        item?.let {
            if (item?.value?.author != null) {
                var currentText = item?.value?.published
                date = currentText?.changeDateFrom() ?: "NA"
            }
        }
        return date
    }

    fun getImageDetailsToShare(): String {
        var shareText = "I would like to share this with you\n"
        shareText += "URL : ${item?.value?.media?.m ?: "URL Not Found"}\n"
        shareText += "Author : ${this.getAuthor()}"
        return shareText
    }


}