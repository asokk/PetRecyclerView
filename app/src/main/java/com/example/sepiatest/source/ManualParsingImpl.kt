package com.example.sepiatest.source

import com.example.sepiatest.api.PetApi
import com.example.sepiatest.models.Pet

class ManualParsingImpl : PetApi {
    override fun getPets(): List<Pet> {
        return listOf(
            Pet("","","","")
        )
    }
}