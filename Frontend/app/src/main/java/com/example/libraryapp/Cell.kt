package com.example.libraryapp

data class Cell(
        val id: Int,
        val name: String,
        val author: String,
        val description: String,
        val photo: String,
        val category: Array<Int>,
        val bookitems: String,
)

data class CellImage(
        val item: String,
        val id: Int,
        val name: String,
        val author: String,
        val description: String,
        val photo: String,
        val category: Array<Int>,
        val bookitems: String,
)

data class CellBookItem(
        val id: Int,
        val shelf: String,
        val condition: String,
        val book: String,
)