package com.flowz.sixtjobapp2.presentation.cars_list

import androidx.lifecycle.*
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp.domain.usecases.GetCarsUseCase
import com.plcoding.cryptocurrencyappyt.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class  CarsApiStatus {LOADING, ERROR, DONE}


@HiltViewModel
class CarsViewModel @Inject constructor(private val getCarsUseCase: GetCarsUseCase) :ViewModel() {



       val carsFromNetwork = MutableLiveData<List<Car>>()
       val requestCarsNetworkStatus = MutableLiveData<CarsApiStatus>()


     fun getCars() {

        getCarsUseCase().onEach { result->

            when(result){
                is Resource.Success ->{

                    requestCarsNetworkStatus.value = CarsApiStatus.DONE

                    carsFromNetwork.postValue(result.data!!)


                }
                is Resource.Error ->{

                    requestCarsNetworkStatus.value = CarsApiStatus.ERROR

                }
                is Resource.Loading ->{

                    requestCarsNetworkStatus.value = CarsApiStatus.LOADING

                }

            }
        }.launchIn(viewModelScope)
    }


}