package com.example.sepiatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.sepiatest.adapter.PetAdapter
import com.example.sepiatest.databinding.ActivityMainBinding
import com.example.sepiatest.models.Pet
import com.example.sepiatest.utils.AssertsManager
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var petItemList = arrayListOf<Pet>()
    lateinit var binding: ActivityMainBinding
    lateinit var petAdapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /* val petsJson = AssertsManager.openStringFile("petslist.json", this)
        val jsonObject = JSONObject(petsJson)
        val jsonArray: JSONArray = jsonObject.getJSONArray("pets")

        for (i in 0 until jsonArray.length()) {
            val item: JSONObject = jsonArray.getJSONObject(i)
            val imageItem = item.getString("image_url")
            val title = item.getString("title")
            val contentUrl = item.getString("content_url")
            val date = item.getString("date_added")

            val pet = Pet(contentUrl, date, imageItem, title)
            petItemList.add(pet)
        }*/

       // petAdapter = PetAdapter(this, petItemList, clickView)
       // binding.bookList.adapter = petAdapter
    }

    private val clickView = object : PetAdapter.OnItemClick {
        override fun onPetItemClick(pet: Pet) {

        }
    }
}