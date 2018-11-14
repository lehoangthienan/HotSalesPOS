package com.uit.daniel.hotsalesmanager.view.product.createproduct

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.location.searchaddresslocation.SearchAddressLocationActivity
import kotlinx.android.synthetic.main.fragment_create_product.*

class CreateProductFragment : Fragment() {

    private lateinit var createGroupViewModel: CreateGroupViewModel
    private lateinit var userManagerUtil: UserManagerUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addEvents()
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
        }
        tvCheckFinish.setOnClickListener {
            isFullField()
        }
        tvAddLocation.setOnClickListener {
            startSearchLocationActivity()
        }
    }

    private fun startSearchLocationActivity() {
        val intent = Intent(activity, SearchAddressLocationActivity::class.java)
        activity.startActivity(intent)
    }

    @SuppressLint("CheckResult")
    private fun isFullField() {
        if (etName.text.toString().isNullOrBlank() ||
            etPrice.text.toString().isNullOrBlank() ||
            etDiscount.text.toString().isNullOrBlank() ||
            etContent.text.toString().isNullOrBlank() ||
            etImageLink.text.toString().isNullOrBlank()
        ) ToastSnackBar.showSnackbar("Please enter full information before proceeding.", view, activity)
        else {
            createProduct()
        }
    }

    @SuppressLint("CheckResult")
    private fun createProduct() {
        createGroupViewModel.createProductObservable().subscribe { check ->
            if (check) {
                setLatLagOfProduct()
                activity.finish()
            }
        }
        createGroupViewModel.createProduct(
            etName.text.toString(),
            etPrice.text.toString().toInt(),
            etDiscount.text.toString().toInt(),
            spCategory.selectedItemPosition,
            etContent.text.toString(),
            etImageLink.text.toString()
        )
    }

    private fun setLatLagOfProduct() {
        userManagerUtil.setLat(0.0)
        userManagerUtil.setLng(0.0)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createGroupViewModel = CreateGroupViewModel(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
    }

}