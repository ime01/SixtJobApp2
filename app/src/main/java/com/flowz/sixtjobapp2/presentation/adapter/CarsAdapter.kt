package com.flowz.agromailjobtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp2.R
import com.flowz.sixtjobapp2.databinding.CarListItemBinding


typealias urlListener = (item: Car) -> Unit


class CarsAdapter  (val listener: urlListener)  :ListAdapter<Car, CarsAdapter.ImageViewHolder>(CarsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ImageViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_list_item, parent, false)

        return ImageViewHolder(CarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
            getItem(it)?.let{item-> listener(item)}
        }

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                userName.text = "Name: ${currentItem.name}"
                make.text = "Car Make: ${currentItem.make}"
                fuelLevel.text = " Fuel Level: ${currentItem.fuelLevel.toString()}"

                val imageLink = currentItem?.carImageUrl

                imageThumbail.load(imageLink){
                    error(R.drawable.bmw)
                    placeholder(R.drawable.bmw)
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

    inner class ImageViewHolder(val binding: CarListItemBinding, private val listener: (Int)-> Unit): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener(adapterPosition)
            }
        }
    }


    interface RowClickListener{
        fun onItemClickListener(car: Car)

    }

}