package com.example.sepiatest.viewmodels

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import com.example.sepiatest.models.Content
import com.example.sepiatest.models.PetsList
import com.example.sepiatest.utils.AssertsManager
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val app: Application = mock()
    private lateinit var mainViewModel: MainViewModel

    val mContextMock = mockk<Context>(relaxed = true)

    @Before
    fun setup() {
        mainViewModel = MainViewModel(app)
    }

    @Test
    fun test_verify_pet_list_json() {
        val petJson  = AssertsManager.loadJsonFile("petslist.json")
        assertNotNull(petJson)

        val petList = Gson().fromJson(petJson, PetsList::class.java)
        assertEquals(10, petList.pets.size)
        assertEquals("Cat", petList.pets[0].title)
        assertEquals("https://en.wikipedia.org/wiki/Cat", petList.pets[0].content_url)
        assertEquals("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Cat_poster_1.jpg/1200px-Cat_poster_1.jpg", petList.pets[0].image_url)
        assertEquals("2018-06-02T03:27:38.027Z", petList.pets[0].date_added)

        assertEquals("Guinea Pig", petList.pets[9].title)
        assertEquals("https://en.wikipedia.org/wiki/Guinea_pig", petList.pets[9].content_url)
        assertEquals("https://upload.wikimedia.org/wikipedia/commons/0/00/Two_adult_Guinea_Pigs_%28Cavia_porcellus%29.jpg", petList.pets[9].image_url)
        assertEquals("2018-08-04T10:45:29.027Z", petList.pets[9].date_added)
    }

    @Test
    fun test_verify_config_json() {
        val configJson  = AssertsManager.loadJsonFile("config.json")
        assertNotNull(configJson)
        val configData = Gson().fromJson(configJson, Content::class.java)
        assertEquals("M-F 9:00 - 18:00", configData.settings.workHours)
    }

    @Test
    fun test_json_asset() {
        //mainViewModel.updatePetList()
    }
}

