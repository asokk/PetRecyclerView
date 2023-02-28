package com.example.sepiatest.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sepiatest.models.Pet
import com.example.sepiatest.models.PetsList
import com.example.sepiatest.utils.AssertsManager
import com.google.gson.Gson

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var petListLiveData = MutableLiveData<List<Pet>>()
    private var petItemList = arrayListOf<Pet>()

    init {
        //loadPetListFromAssets()
    }

    fun loadPetListFromAssets() {
        val petsJson = AssertsManager.getJsonStreamReader("petslist.json", getApplication())
        val petList = Gson().fromJson(petsJson, PetsList::class.java)

        petList.pets.forEach {
            val pet = Pet(it.content_url, it.date_added, it.image_url, it.title)
            petItemList.add(pet)
        }
    }

    fun updatePetList() {
        val petsJson = AssertsManager.getJsonStreamReader("petslist.json", getApplication())
        val petList: PetsList = Gson().fromJson(petsJson, PetsList::class.java)

        petList.pets.forEach {
            val pet = Pet(it.content_url, it.date_added, it.image_url, it.title)
            petItemList.add(pet)
        }

        petListLiveData.value = petItemList
    }
}