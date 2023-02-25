package com.example.sepiatest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sepiatest.databinding.ItemRowBinding
import com.example.sepiatest.models.Pet

class PetAdapter(
    private val context: Context,
    private val petItemList: List<Pet>,
    private val callBack: OnItemClick
) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(petItemList[position])
        holder.itemView.setOnClickListener {
            callBack.onPetItemClick(petItemList[position])
        }
    }

    override fun getItemCount(): Int {
        return petItemList.size
    }

    inner class ViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(petItem: Pet) {
            binding.textPetTitle.text = petItem.title
            Glide.with(context).asBitmap().load(petItem.image_url).into(binding.imagePet)
        }
    }

    interface OnItemClick {
        fun onPetItemClick(pet: Pet)
    }
}