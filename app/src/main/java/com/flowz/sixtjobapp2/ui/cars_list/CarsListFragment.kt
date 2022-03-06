package com.flowz.sixtjobapp2.ui.cars_list

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout.VERTICAL
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.agromailjobtask.adapter.CarsAdapter
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp2.R
import com.flowz.sixtjobapp2.databinding.FragmentCarsListBinding
import com.flowz.sixtjobapp2.presentation.cars_list.CarsApiStatus
import com.flowz.sixtjobapp2.presentation.cars_list.CarsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsListFragment : Fragment(R.layout.fragment_cars_list) {

    private var _binding: FragmentCarsListBinding? = null
    private val binding get() = _binding!!
    lateinit var carAdapter  : CarsAdapter

    private val viewModel: CarsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCarsListBinding.bind(view)

        showWelcomeMarqueeText()

        carAdapter = CarsAdapter{
            transitionToDetailView(it)
        }

        viewModel.getCars()
        observeState()

    }

    private fun showWelcomeMarqueeText() {

        binding.apply {

            welcomeTextMarquee.apply {
                setSingleLine()
                ellipsize = TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                isSelected = true
            }
        }
    }

    fun observeState(){

        binding.apply {

            viewModel.requestCarsNetworkStatus.observe(viewLifecycleOwner, Observer { state ->

                state?.also {
                    when (it) {
                        CarsApiStatus.ERROR -> {

                            errorImage.isVisible = true
                            errorText.isVisible = true

                                showSnackbar(welcomeTextMarquee, getString(R.string.error))

                        }
                        CarsApiStatus.LOADING -> {

                            shimmerFrameLayout.startShimmer()
                            shimmerFrameLayout.visibility = View.VISIBLE

                        }

                        CarsApiStatus.DONE -> {

                            viewModel.carsFromNetwork.observe(viewLifecycleOwner, Observer {
                                    loadRecyclerView(it)

                            })

                        }

                    }
                }

            })
        }

    }

    private fun transitionToDetailView(car: Car) {

        val action = CarsListFragmentDirections.actionCarsListFragmentToCarsDetailsFragment()
        action.car = car
        Navigation.findNavController(requireView()).navigate(action)
    }


    private fun loadRecyclerView(cars: List<Car>) {

        binding.apply {
            errorImage.isVisible = false
            carAdapter.submitList(cars)
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = carAdapter
            val decoration = DividerItemDecoration(requireContext(), VERTICAL)
            rvList.addItemDecoration(decoration)

            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE

            openMapView.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_carsListFragment_to_mapFragment)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}