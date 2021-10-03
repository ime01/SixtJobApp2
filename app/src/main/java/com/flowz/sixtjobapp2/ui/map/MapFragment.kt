package com.flowz.sixtjobapp2.ui.map

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp2.R
import com.flowz.sixtjobapp2.databinding.FragmentMapBinding
import com.flowz.sixtjobapp2.presentation.cars_list.CarsApiStatus
import com.flowz.sixtjobapp2.presentation.cars_list.CarsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private var car: Car? = null
    private var carbitmap: Bitmap? = null
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    var cars = ArrayList<Car>()
    var carIcons = ArrayList<Bitmap>()
    private val viewModel: CarsViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMapBinding.bind(view)

        fetchListOfCarsLocations()


        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        loadCarIcons("https://cdn.sixt.io/codingtask/images/mini.png")
//        loadCarIcons("http://cdn.sixt.io/codingtask/images/mini.png")




//        cars.forEach {
//            loadCarIcons(it.carImageUrl)
//            Log.e("LOAD", it.carImageUrl)
//        }


    }


    private fun loadCarIcons(url: String): Bitmap? {
        Glide.with(requireContext())
            .asBitmap()
            .load(url)
            .placeholder(R.drawable.bmw)
            .error(R.drawable.bmw)
            .fallback(R.drawable.bmw)
            .into(object : CustomTarget<Bitmap>(50,50){
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
//                    imageView.setImageBitmap(resource)
                    Log.e("IMAGE", bitmap.byteCount.toString())
                    Log.e("IMAGE1", bitmap.toString())
                    carIcons.add(bitmap)
                    carbitmap = bitmap

                }
                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

        return  carbitmap

    }

    fun fetchListOfCarsLocations(){

        binding.apply {

            viewModel.requestCarsNetworkStatus.observe(viewLifecycleOwner, Observer { state ->

                state?.also {
                    when (it) {
                        CarsApiStatus.ERROR -> {

                            mapsErrorText.isVisible = true

                            showSnackbar(mapsErrorText, getString(R.string.error))

                        }
                        CarsApiStatus.LOADING -> {

                            mapsProgressBar.isVisible = true

                        }

                        CarsApiStatus.DONE -> {

                            viewModel.carsFromNetwork.observe(viewLifecycleOwner, Observer {

                              it.forEach {
                                  cars.add(it)
                              }

                            })

                        }

                    }
                }

            })
        }

    }

    override fun onMapReady(gmap: GoogleMap?) {

        if (gmap != null) {
            mMap = gmap
        }

        cars.forEach {

            loadCarIcons(it.carImageUrl)
            Log.e("LOAD", it.carImageUrl)


            mMap.addMarker(MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromBitmap(carIcons.get(0)))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.minibmw))
                .position(LatLng(it.latitude, it.longitude)).title(" ${it.name}  driving a ${it.make}"))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12.5F), 4000, null)


        }

    }


}