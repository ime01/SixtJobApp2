package com.flowz.agromailjobtask.adapter

import androidx.recyclerview.widget.DiffUtil
import com.flowz.sixtjobapp.domain.model.Car


class CarsDiffCallback : DiffUtil.ItemCallback<Car>(){
    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem == newItem
    }
}