package com.flowz.sixtjobapp.domain.usecases

import com.flowz.sixtjobapp.data.remote.dto.CarDto
import com.flowz.sixtjobapp.data.remote.dto.toCar
import com.flowz.sixtjobapp.domain.model.Car
import com.flowz.sixtjobapp.domain.repository.CarsRepository
import com.plcoding.cryptocurrencyappyt.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCarsUseCase @Inject constructor (private val repository: CarsRepository) {


    operator fun invoke (): Flow<Resource<List<Car>>> = flow {


       /* try {
            val emptyCars = listOf<Car>()
            emit(Resource.loading())

            val cars = repository.getCars().map { it.toCar() }

            emit(Resource.success(cars))

        }catch (e:HttpException){

            emit(Resource.error(e.toString() ?: "An unexpected error occurred"))

        }catch (e: IOException){

            emit(Resource.error("Couldn't reach the server, Check your internet connection and try again"))
        }*/




           flow {
               emit(repository.getCars().map { it.toCar() })
           }

    }

}