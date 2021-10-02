package com.flowz.sixtjobapp2.presentation

import com.flowz.sixtjobapp.domain.model.Car

data class CarsListState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val error: String = ""
)