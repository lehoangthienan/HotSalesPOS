package com.uit.daniel.hotsalesmanager.view.salesmanager.bottomnavigation

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerViewModel

class ProfileFragment : Fragment() {

    private lateinit var salesManagerViewModel: SalesManagerViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        salesManagerViewModel= SalesManagerViewModel(context)
    }

    override fun onResume() {
        super.onResume()
    }

}