package com.example.sepiatest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sepiatest.R
import com.example.sepiatest.adapter.PetAdapter
import com.example.sepiatest.databinding.FragmentPetBinding
import com.example.sepiatest.models.Pet
import com.example.sepiatest.models.PetsList
import com.example.sepiatest.utils.AssertsManager
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PetListFragment : Fragment() {
    private var petItemList = arrayListOf<Pet>()
    private lateinit var petAdapter: PetAdapter
    private lateinit var petsList: PetsList

    var isValidWeekDay = false

    lateinit var binding: FragmentPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val petsJson = AssertsManager.openStringFile("petslist.json", requireContext())
        val gson = Gson().fromJson(petsJson, PetsList::class.java)

        gson.pets.forEach {
            val pet = Pet(it.content_url, it.date_added, it.image_url, it.title)
            petItemList.add(pet)
        }

        petsList = PetsList(petItemList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        petAdapter = PetAdapter(requireContext(), petsList.pets, clickView)
        binding.bookList.adapter = petAdapter
    }

    private val clickView = object : PetAdapter.OnItemClick {
        override fun onPetItemClick(pet: Pet) {
            val contentJson = AssertsManager.openStringFile("config.json", requireContext())
            val contentJsonObject = JSONObject(contentJson)

            // currentDate.time = 49260000 //20260000

            val data = contentJsonObject.getJSONObject("settings")

            val workingHours = data.getString("workHours")
            val result: List<String> = workingHours.split(" ")

            val formatter = SimpleDateFormat("HH:mm")
            val date1: Date = formatter.parse(result[1]) as Date
            val date2: Date = formatter.parse(result[3]) as Date

            val weekDay = result[0]

            val calendar = Calendar.getInstance()
            val currentData = formatter.format(calendar.time)
            val currentDate: Date = formatter.parse(currentData) as Date

            val allowedWeekDays = mutableListOf<String>()

            if (weekDay == result[0]) {
                allowedWeekDays.add("Mon")
                allowedWeekDays.add("Tue")
                allowedWeekDays.add("Wed")
                allowedWeekDays.add("Thu")
                allowedWeekDays.add("Fri")
            }

            isValidWeekDay = allowedWeekDays.contains(formatter.format(Date()))

            if (currentDate.time <= date1.time || currentDate.time >= date2.time || isValidWeekDay.not()) {
                showPopup(workingHours)
            } else {
                val bundle = bundleOf("content_url" to pet.content_url)
                findNavController().navigate(
                    R.id.action_petListFragment_to_PetDescriptionFragment,
                    bundle
                )
            }
        }
    }

    private fun showPopup(workingHours: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Only Open At")
        alertDialogBuilder.setMessage(workingHours)

        alertDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
            Toast.makeText(
                requireContext(),
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
        }
        alertDialogBuilder.show()
    }
}