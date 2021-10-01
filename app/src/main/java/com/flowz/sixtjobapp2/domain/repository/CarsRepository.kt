package com.flowz.sixtjobapp.domain.repository

import com.flowz.sixtjobapp.data.remote.dto.CarDto

interface CarsRepository {

    suspend fun getCars(): List<CarDto>

}