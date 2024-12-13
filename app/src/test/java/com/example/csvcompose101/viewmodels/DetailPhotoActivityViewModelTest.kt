package com.example.csvcompose101.viewmodels

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailPhotoActivityViewModelTest {

    private val detailPhotoActivityViewModel = DetailPhotoActivityViewModel()

    @Before
    fun setUpViewModel() {
        val jsonText = (Gson().toJson(SampleData.getIndividualItem()))
        detailPhotoActivityViewModel.initializeItem(jsonText)
    }

    @Test
    fun getFormattedDateTest() {
        val formattedDate = detailPhotoActivityViewModel.getFormattedDate()
        assertEquals("23:11 PM 2024-12-11", formattedDate)
    }

    @Test
    fun getAuthorTest() {
        val author = detailPhotoActivityViewModel.getAuthor()
        assertEquals("Fruitcake Enterprises", author)
    }

    @Test
    fun getImageDetailsToShareTest() {
        val share = detailPhotoActivityViewModel.getImageDetailsToShare()
        println("Author : $share")
        val expected = "I would like to share this with you\n" +
                "URL : https://live.staticflickr.com/65535/54180499019_36ba01473a_m.jpg\n" +
                "Author : Fruitcake Enterprises"
        assertEquals(expected, share)
    }
}