package com.flowz.sixtjobapp2.ui.car_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp2.R
import com.flowz.sixtjobapp2.databinding.FragmentCarsDetailsBinding


class CarsDetailsFragment : Fragment(R.layout.fragment_cars_details) {

    private var _binding: FragmentCarsDetailsBinding? = null
    private val binding get() = _binding!!
    private var car: Car? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            car = CarsDetailsFragmentArgs.fromBundle(it).car
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCarsDetailsBinding.bind(view)

        binding.apply {

            car?.apply {

              duserName.text = "Name :  ${name}"
              dinnerCleanliness.text = "Inner Cleanliness :  ${innerCleanliness}"
              dseries.text = "Series :  ${series}"
              dmodelName.text = "Model Name :  ${modelName}"
              dmake.text = "Make :  ${make}"
              dlongitude.text = "Longitude :  ${longitude}"
              dlatitude.text = "Latitude :  ${latitude}"
              dgroup.text = "Group :  ${group}"
              dfuelLevel.text = "Fuel Level :  ${fuelLevel}"
            }


            Glide.with(imageDetailLarge)
                .load(car?.carImageUrl)
                .placeholder(R.drawable.bmw)
                .error(R.drawable.bmw)
                .fallback(R.drawable.bmw)
                .into(imageDetailLarge)


        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}