package com.example.libraryapp.data.api.models

import com.google.gson.annotations.SerializedName

data class BookItem(

        @SerializedName("id")
        val id: Int,
        @SerializedName("shelf")
        val shelf: String,
        @SerializedName("condition")
        val condition: String,
        @SerializedName("book")
        val book: String,
)