package com.uit.daniel.hotsalesmanager.utils

import android.location.Location

class LocationManagerUtils {

    fun getDistanceLocation(currentUserLocation: Location, locationAddress: Location): Float {
        var distanceLocation = 0.0f
        if (currentUserLocation != null && locationAddress != null) {
            distanceLocation = currentUserLocation.distanceTo(locationAddress)
        }
        return distanceLocation
    }

//    fun getAddressLocationName(context: Context, lat: Double, lng: Double): String {
//        val geocoder = Geocoder(context, Locale.getDefault())
//        val addresses: List<Address>
//        addresses = geocoder.getFromLocation(lat, lng, 1)
//        val address = addresses?.getOrNull(0)?.getAddressLine(0)
//        if (address != null) return address
//        return ""
//    }

}