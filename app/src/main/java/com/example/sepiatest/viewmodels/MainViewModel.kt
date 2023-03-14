package com.example.sepiatest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sepiatest.models.Pet
import com.example.sepiatest.models.PetsList
import com.example.sepiatest.utils.AssertsManager
import com.example.sepiatest.utils.Resource

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var petListLiveData = MutableLiveData<Resource<List<Pet>>>()
    private var petItemList = arrayListOf<Pet>()

    fun updatePetList() {
        val petsJson = AssertsManager.openStringFile("petslist.json", getApplication())
        val petList = AssertsManager.convertJsonStringToObject(petsJson, PetsList::class.java)

        if (petList.pets.isEmpty()) {
            petListLiveData.postValue(Resource.error("NO DATA", null))
        } else {
            petList.pets.forEach {
                val pet = Pet(it.content_url, it.date_added, it.image_url, it.title)
                petItemList.add(pet)
            }
        }

        //petListLiveData.postValue(Resource.success(petItemList))
        petListLiveData.value = Resource.success(petItemList)
    }
}