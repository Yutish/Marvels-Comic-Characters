package com.example.marvelscomiccharacters.data.data_source.dto.CharacterDTO

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)