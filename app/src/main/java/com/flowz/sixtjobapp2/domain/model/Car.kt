package com.flowz.sixtjobapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Car(
    val carImageUrl: String,
    val color: String,
    val fuelLevel: Double,
    val group: String,
    val id: String,
    val innerCleanliness: String,
    val latitude: Double,
    val licensePlate: String,
    val longitude: Double,
    val make: String,
    val modelName: String,
    val name: String,
    val series: String,
): Parcelable
