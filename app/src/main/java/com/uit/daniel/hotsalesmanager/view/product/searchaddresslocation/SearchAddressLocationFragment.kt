package com.uit.daniel.hotsalesmanager.view.product.searchaddresslocation

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.location.*
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.base.BaseFragment
import com.uit.daniel.hotsalesmanager.utils.LocationUtils
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
import com.uit.daniel.hotsalesmanager.view.custom.searchaddresslocation.PlacesAdapter
import kotlinx.android.synthetic.main.fragment_search_address_location.*
import java.io.IOException
import java.util.*

class SearchAddressLocationFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var gmap: GoogleMap
    private lateinit var placesAdapter: PlacesAdapter
    private lateinit var latLng: LatLng
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var geoDataClient: GeoDataClient
    private lateinit var settingsClient: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var isAutoCompleteLocation = false
    private lateinit var location: Location
    private lateinit var LLB_CURRENT_LOCATION: LatLngBounds
    private lateinit var userManagerUtil: UserManagerUtil
    private var lng: String = ""
    private var log: String = ""
    private var locationUtils = LocationUtils.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_address_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        progressLoadMap()
        searchLocationOnMap()
        addEvents()
    }

    private fun progressLoadMap() {
        progressBarAddLocation.visibility = getVisibilityView(true)
    }

    private fun init() {
        progressBarAddLocation.isIndeterminate = true

        LLB_CURRENT_LOCATION = LatLngBounds(
            LatLng(10.850022, 106.774403),
            LatLng(10.850022, 106.774403)
        )
    }

    private fun addEvents() {
        tvFinishAddressSearch.setOnClickListener {
            if (!actAddressSearch.text.toString().isNullOrBlank()) userManagerUtil.setAddressLocation(actAddressSearch.text.toString())
            activity?.finish()
        }
        tvBackAddressSearch.setOnClickListener {
            activity?.finish()
        }
    }

    private fun searchLocationOnMap() {
        val typeFilter = AutocompleteFilter.Builder().setCountry("VN").build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val loc = locationResult!!.lastLocation
                if (!isAutoCompleteLocation) {
                    location = loc
                    latLng = LatLng(location.latitude, location.longitude)
                    assignToMap()
                }
            }

        }
        locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval((10 * 1000).toLong())        // 10 seconds, in milliseconds
            .setFastestInterval((6 * 1000).toLong()) // 1 second, in milliseconds

        settingsClient = LocationServices.getSettingsClient(activity!!)
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        locationSettingsRequest = builder.build()

        placesAdapter = PlacesAdapter(
            activity!!,
            R.layout.layout_custom_list_result_search_location,
            geoDataClient,
            typeFilter,
            LLB_CURRENT_LOCATION
        )
        actAddressSearch.setAdapter(placesAdapter)
        actAddressSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    cancel.visibility = View.VISIBLE
                } else {
                    cancel.visibility = View.GONE
                }
            }
        })
        actAddressSearch.setOnItemClickListener { _, _, position, _ ->
            hideKeyboard()
            val item = placesAdapter.getItem(position)
            val placeId = item?.placeId
            item?.getPrimaryText(null)

            val placeResult = geoDataClient.getPlaceById(placeId)
            placeResult.addOnCompleteListener { task ->
                val places = task.result
                places?.firstOrNull()?.let { place ->
                    isAutoCompleteLocation = true
                    latLng = place.latLng
                    assignToMap()
                }

                places?.release()
            }
            //can get primaryText when clicked in here
        }
        cancel.setOnClickListener {
            actAddressSearch.setText("")
        }
    }

    private fun hideKeyboard() {
        try {
            val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun assignToMap() {
        gmap.clear()
        lng = latLng.latitude.toString()
        log = latLng.longitude.toString()

        val options = MarkerOptions()
            .position(latLng)
            .title(actAddressSearch.text.toString())
        gmap.apply {
            addMarker(options)
            moveCamera(CameraUpdateFactory.newLatLng(latLng))
            animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
        geoDataClient = Places.getGeoDataClient(context, null)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        setDismissProgressLoadMapDialog()
        setMarkerWhenTouch()
        getAddressLocation()
    }

    private fun setDismissProgressLoadMapDialog() {
        gmap.setOnMapLoadedCallback {
            progressBarAddLocation.visibility = getVisibilityView(false)
        }
    }

    private fun getAddressLocation() {
        val geoCoder = Geocoder(activity, Locale.getDefault())
        try {
            val addresses = geoCoder.getFromLocationName("Ho Chi Minh City", 5)
            if (addresses.size > 0) {
                val lat = addresses[0].latitude
                val lon = addresses[0].longitude

                Log.d("lat-long", "$lat.......$lon")
                val user = LatLng(lat, lon)
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 15f))
                gmap.animateCamera(CameraUpdateFactory.zoomTo(10f), 2000, null)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun setMarkerWhenTouch() {
        gmap.setOnMapClickListener { point ->
            gmap.clear()
            val geocoder = Geocoder(activity, Locale.getDefault())
            val marker = MarkerOptions()
                .position(LatLng(point.latitude, point.longitude))
                .title("Location you want to delivery.")
            gmap.addMarker(marker)

            setTextOfAutoCompleteTextViewAndSetAddressLocation(geocoder, point)
        }
    }

    private fun setTextOfAutoCompleteTextViewAndSetAddressLocation(geocoder: Geocoder, point: LatLng?) {
        actAddressSearch.setText(point?.let {
            locationUtils.setAddressesLocation(geocoder, it)[0].getAddressLine(0).toString()
        })
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