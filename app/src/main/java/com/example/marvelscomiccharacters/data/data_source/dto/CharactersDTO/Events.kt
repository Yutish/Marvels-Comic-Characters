package com.example.marvelscomiccharacters.data.data_source.dto.CharactersDTO

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)