package com.uit.daniel.hotsalesmanager.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import foundation.dwarves.findfriends.utils.Constant
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
}