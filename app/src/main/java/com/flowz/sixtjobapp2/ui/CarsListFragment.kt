package com.flowz.sixtjobapp2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.flowz.sixtjobapp2.R
import com.flowz.sixtjobapp2.databinding.FragmentCarsListBinding
import com.flowz.sixtjobapp2.presentation.cars_list.CarsApiStatus
import com.flowz.sixtjobapp2.presentation.cars_list.CarsListState
import com.flowz.sixtjobapp2.presentation.cars_list.CarsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsListFragment : Fragment(R.layout.fragment_cars_list) {

    private var _binding: FragmentCarsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarsViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCarsListBinding.bind(view)

        viewModel.getCars()
        observeState()



    }


    fun observeState(){

        binding.apply {

            viewModel.requestCarsNetworkStatus.observe(viewLifecycleOwner, Observer { state ->

                state?.also {
                    when (it) {
                        CarsApiStatus.ERROR -> {

                            apiText.text = "ERROR OCCURED"

                        }
                        CarsApiStatus.LOADING -> {

                            apiText.text = "API IS LOADING NOW"

                        }

                        CarsApiStatus.DONE -> {

                            viewModel.carsFromNetwork.observe(viewLifecycleOwner, Observer {
                                apiText.text = it.toString()
                            })

                        }

                    }
                }

            })
        }

    }



}