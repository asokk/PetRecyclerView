package com.example.sepiatest.api

import com.example.sepiatest.models.Pet

interface PetApi {
    fun getPets(): List<Pet>
}