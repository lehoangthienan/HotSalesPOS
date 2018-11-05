package com.uit.daniel.hotsalesmanager.view.custom.countrycode

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.uit.daniel.hotsalesmanager.R
import kotlinx.android.synthetic.main.spinner_custom_dropdown.view.*
import kotlinx.android.synthetic.main.spinner_custom_view.view.*

class CustomAdapter(context: Context, resourceID: Int, var countries: ArrayList<Country>) :
    ArrayAdapter<Country>(context, resourceID, countries) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.spinner_custom_view, parent, false)

        view.imgFlagCountryView.setImageResource(countries[position].flag)
        view.tvCodeCountryView.text = countries[position].code

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.spinner_custom_dropdown, parent, false)

        view.imgFlagCountryDropDown.setImageResource(countries[position].flag)
        view.tvCodeCountryDropDown.text = countries[position].code
        view.tvNameCountryDropDown.text = countries[position].name

        return view
    }
}
