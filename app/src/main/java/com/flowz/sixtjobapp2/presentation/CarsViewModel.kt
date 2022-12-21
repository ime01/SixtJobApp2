package com.flowz.sixtjobapp2.presentation.cars_list

import androidx.lifecycle.*
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp.domain.usecases.GetCarsUseCase
import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.common.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

//enum class  CarsApiStatus {LOADING, ERROR, DONE}


@HiltViewModel
class CarsViewModel @Inject constructor(private val getCarsUseCase: GetCarsUseCase) :ViewModel() {



       val carsFromNetwork = MutableLiveData<Resource<List<Car>>>()
      // val requestCarsNetworkStatus = MutableLiveData<CarsApiStatus>()
    init {
        getCars()
    }


     fun getCars() {

        getCarsUseCase().onEach { result->

            when(result.status){

                 Status.SUCCESS->{

                    carsFromNetwork.value = Resource.success(result.data!!)

                }
                 Status.ERROR ->{

                    carsFromNetwork.value = Resource.error( result.message!!)

                }
                 Status.LOADING ->{

                    carsFromNetwork.value = Resource.loading()

                }

            }
        }.launchIn(viewModelScope)



       /*  viewModelScope.launch {
             getCarsUseCase.invoke()
                 .catch { e ->
                 carsFromNetwork.value = Resource.error(e.toString())
                }
                 .collect {
                     carsFromNetwork.value = Resource.success (it.data)
                 }
         }*/

    }


}