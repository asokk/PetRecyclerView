package com.example.sepiatest.fragments

import android.R.string.yes
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sepiatest.R
import com.example.sepiatest.adapter.PetAdapter
import com.example.sepiatest.databinding.FragmentPetBinding
import com.example.sepiatest.models.Content
import com.example.sepiatest.models.Pet
import com.example.sepiatest.utils.AssertsManager
import com.example.sepiatest.viewmodels.MainViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class PetListFragment : Fragment() {
    private lateinit var petAdapter: PetAdapter

    private lateinit var mainViewModel: MainViewModel

    var isValidWeekDay = false

    lateinit var binding: FragmentPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel =
            ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainViewModel.updatePetList()
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

        mainViewModel.petListLiveData.observe(requireActivity(), androidx.lifecycle.Observer {
            petAdapter = PetAdapter(requireContext(), it, clickView)
            binding.bookList.adapter = petAdapter
        })

    }

    private val clickView = object : PetAdapter.OnItemClick {
        @SuppressLint("SimpleDateFormat")
        override fun onPetItemClick(pet: Pet) {
            val configJson = AssertsManager.openStringFile("config.json", requireContext())
            val configData = Gson().fromJson(configJson, Content::class.java)

            val result: List<String> = configData.settings.workHours.split(" ")

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

            val dayFormatter = SimpleDateFormat("EEE")
            isValidWeekDay = allowedWeekDays.contains(dayFormatter.format(Date()))

            if (currentDate.time <= date1.time || currentDate.time >= date2.time || isValidWeekDay.not()) {
                showPopup(configData.settings.workHours)
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

        alertDialogBuilder.setPositiveButton(yes) { _, _ ->
            Toast.makeText(
                requireContext(),
                yes, Toast.LENGTH_SHORT
            ).show()
        }
        alertDialogBuilder.show()
    }
}