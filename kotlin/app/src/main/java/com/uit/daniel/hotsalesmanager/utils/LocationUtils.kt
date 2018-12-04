package com.uit.daniel.hotsalesmanager.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.util.*

class LocationUtils {

    companion object : SingletonHolder<LocationUtils>(::LocationUtils)

    fun setAddressesLocation(geocoder: Geocoder, point: LatLng): List<Address> {
        return geocoder.getFromLocation(point.latitude, point.longitude, 1)
    }

    fun getAddressLocationName(context: Context, lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>
        addresses = geocoder.getFromLocation(lat, lng, 1)
        val address = addresses?.getOrNull(0)?.getAddressLine(0)
        if (address != null) return address
        return ""
    }

    fun getDistanceLocation(context: Context, locationAddress: Location): Int {
        val userManagerUtil: UserManagerUtil = UserManagerUtil.getInstance(context)

        val lat: Double = userManagerUtil.getLat()
        val lng: Double = userManagerUtil.getLng()

        val currentLocation = Location("CurrentLocation")
        currentLocation.latitude = lat
        currentLocation.longitude = lng

        var distanceLocation = 0
        if (currentLocation != null && locationAddress != null) {
            distanceLocation = currentLocation.distanceTo(locationAddress).toInt()
        }
        return distanceLocation
    }
}