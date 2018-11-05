package com.uit.daniel.hotsalesmanager.view.custom.searchaddresslocation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.google.android.gms.common.data.DataBufferUtils
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompletePrediction
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.android.gms.tasks.Tasks
import com.uit.daniel.hotsalesmanager.R
import kotlinx.android.synthetic.main.layout_custom_list_result_search_location.view.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by AnLe on 30/9/18.
 */
class PlacesAdapter(
    context: Context,
    resourceId: Int,
    geoData: GeoDataClient,
    filter: AutocompleteFilter?,
    private val llbCurrentLocation: LatLngBounds
) : ArrayAdapter<AutocompletePrediction>(context, resourceId), Filterable {

    var resultList: MutableList<AutocompletePrediction> = ArrayList()
    private val geoDataClient = geoData
    private val placeFilter = filter

    override fun getCount(): Int {
        return resultList.size
    }

    override fun getItem(position: Int): AutocompletePrediction? {
        return resultList[position]
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.layout_custom_list_result_search_location, parent, false)

        view.ivAddLocationSearchMap.setOnClickListener {
            (context as Activity).finish()
        }
        val placeResult = geoDataClient.getPlaceById(item?.placeId)
        placeResult.addOnCompleteListener { task ->
            val places = task.result
            places?.firstOrNull()?.let { place ->
            }
            places?.release()
        }
        val tvNameLocationSearchMap = view.findViewById<View>(R.id.tvNameLocationSearchMap) as TextView
        tvNameLocationSearchMap.text = item?.getPrimaryText(null)

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val results = Filter.FilterResults()
                var filterData: ArrayList<AutocompletePrediction>? = ArrayList()
                if (constraint != null) {
                    filterData = getAutocomplete(constraint)
                }

                results.values = filterData
                if (filterData != null) {
                    results.count = filterData.size
                } else {
                    results.count = 0
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
                Log.v("results", "results==$results")
                if (results != null && results.count > 0) {
                    resultList = results.values as ArrayList<AutocompletePrediction>
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

            override fun convertResultToString(resultValue: Any): CharSequence {
                return if (resultValue is AutocompletePrediction) {
                    resultValue.getPrimaryText(null)
                } else {
                    super.convertResultToString(resultValue)
                }
            }
        }
    }

    private fun getAutocomplete(constraint: CharSequence): ArrayList<AutocompletePrediction>? {
        val results = geoDataClient.getAutocompletePredictions(
            constraint.toString(), llbCurrentLocation,
            placeFilter
        )
        try {
            Tasks.await(results, 60, TimeUnit.SECONDS)
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: TimeoutException) {
            e.printStackTrace()
        }

        return try {
            val autocompletePredictions = results.result
            DataBufferUtils.freezeAndClose<AutocompletePrediction, AutocompletePrediction>(autocompletePredictions)
        } catch (e: RuntimeExecutionException) {
            null
        }

    }

}