package com.example.csvcompose101.utilities

import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormatterUtilityTest {

    @Test
    fun addition_isCorrect() {
        val currentDate = "2024-11-19T18:46:26-08:00"
        val changeDateFrom = currentDate.changeDateFrom()
        assertEquals("20:46 PM 2024-11-19", changeDateFrom)
        println("changeDateFrom : $changeDateFrom")
    }
}