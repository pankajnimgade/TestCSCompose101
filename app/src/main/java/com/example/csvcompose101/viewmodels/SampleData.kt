package com.example.csvcompose101.viewmodels

import com.example.csvcompose101.data.models.Item
import com.example.csvcompose101.data.models.Media

object SampleData {

    fun getListOfPhotos(): List<Item> {
        val list: MutableList<Item> = mutableListOf()
        for (index in 0..11) {
            list.add(
                Item(
                    author = "nobody@flickr.com (\"Fruitcake Enterprises\")",
                    author_id = "26861346@N00",
                    date_taken = "2024-12-05T17:46:14-08:00",
                    description = " <p><a href=\"https://www.flickr.com/people/machu_picchu/\">Fruitcake Enterprises</a> posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/machu_picchu/54186170341/\" title=\"12052024-29\"><img src=\"https://live.staticflickr.com/65535/54186170341_a5b98502ff_m.jpg\" width=\"240\" height=\"180\" alt=\"12052024-29\" /></a></p> <p>Alexia and I saw that there was an interactive animal program in one of the buildings between 5:30 and 6:00, so we went in and saw roughly the last half of the program.<br /> <br /> Here we got to see Molly the porcupine, eating a snack out of a bag. Honestly she creeped me out more than the snake. Wild animals should not have front paws that operate that much like human hands!</p> ",
                    link = "",
                    media = Media("https://live.staticflickr.com/65535/54180499019_36ba01473a_m.jpg"),
                    published = "",
                    tags = "",
                    title = "Fruitcake Enterprises"
                )
            )
        }
        return list.toList()
    }

    fun getIndividualItem(): Item {
        return Item(
            author = "nobody@flickr.com (\"Fruitcake Enterprises\")",
            author_id = "26861346@N00",
            date_taken = "2024-12-05T17:46:14-08:00",
            description = "Alexia and I saw that there was an interactive animal program in one of the buildings between 5:30 and 6:00.",
            link = "",
            media = Media("https://live.staticflickr.com/65535/54180499019_36ba01473a_m.jpg"),
            published = "2024-12-11T21:11:14-08:00",
            tags = "Fruitcake Enterprises",
            title = "Fruitcake Enterprises"
        )
    }
}