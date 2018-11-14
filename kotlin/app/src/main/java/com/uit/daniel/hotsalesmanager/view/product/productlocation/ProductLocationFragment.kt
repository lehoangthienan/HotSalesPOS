package com.uit.daniel.hotsalesmanager.view.product.productlocation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.base.BaseFragment
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
import kotlinx.android.synthetic.main.fragment_product_location.*

class ProductLocationFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var gmap: GoogleMap
    private var lat: Double = 0.0
    private var lng: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLatLng()
        addEvents()
    }

    private fun addEvents() {
        tvBackAddressSearch.setOnClickListener {
            activity?.finish()
        }
    }

    private fun setMarker() {
        val marker = MarkerOptions()
            .position(LatLng(lat, lng))
            .title("Location of product you want to buy.")
        gmap.addMarker(marker)
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 14f))
    }

    private fun getLatLng() {
        lat = activity?.intent?.getDoubleExtra("LAT", 0.0) ?: 0.0
        lng = activity?.intent?.getDoubleExtra("LNG", 0.0) ?: 0.0
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        setDismissProgressLoadMapDialog()
        setMarker()
    }

    private fun setDismissProgressLoadMapDialog() {
        gmap.setOnMapLoadedCallback {
            progressBarAddLocation.visibility = getVisibilityView(false)
        }
    }

    override fun setUpView(view: View, savedInstanceState: Bundle?) {
        mapViewAddressSearch.onCreate(savedInstanceState)
        mapViewAddressSearch.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapViewAddressSearch?.onResume()
    }

    override fun onPause() {
        mapViewAddressSearch?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapViewAddressSearch?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewAddressSearch?.onLowMemory()
    }
}