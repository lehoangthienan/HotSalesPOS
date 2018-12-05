package com.uit.daniel.hotsalesmanager.view.product.updateproduct

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.IntentUtils
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import kotlinx.android.synthetic.main.fragment_update_product.*

class UpdateProductFragment : Fragment() {

    private lateinit var updateProductViewModel: UpdateProductViewModel
    private lateinit var productResponse: ProductResponse
    private var productId = ""
    private var imageUrl = ""
    private lateinit var bottomSheetDialogUploadImage: BottomSheetDialog
    private val intentUtils = IntentUtils()
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReferenceFromUrl("gs://hotsalesmanager-fef89.appspot.com")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductId()
        getProductDetail()
        addEvents()
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvCheckFinish.setOnClickListener {
            isFullField()
        }
    }

    private fun isFullField() {
        if (etName.text.toString().isNullOrBlank() ||
            etPrice.text.toString().isNullOrBlank() ||
            etDiscount.text.toString().isNullOrBlank() ||
            etContent.text.toString().isNullOrBlank() ||
            imageUrl.isNullOrBlank()
        ) ToastSnackBar.showSnackbar("Please enter full information before proceeding.", view, activity)
        else {
            updateProduct()
        }
    }

    @SuppressLint("CheckResult")
    private fun updateProduct() {
        updateProductViewModel.updateProductObservable().subscribe { check ->
            if (check) {
                activity.finish()
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            } else ToastSnackBar.showSnackbar("Update product fail!", view, activity)
        }
        setProductResponse()
        updateProductViewModel.updateProduct(productId, productResponse)
    }

    private fun setProductResponse() {
        productResponse.result!![0].name = etName.text.toString()
        productResponse.result!![0].price = etPrice.text.toString().toInt()
        productResponse.result!![0].discount = etDiscount.text.toString().toInt()
        productResponse.result!![0].content = etContent.text.toString()
        productResponse.result!![0].image = imageUrl
    }

    @SuppressLint("CheckResult")
    private fun getProductDetail() {
        updateProductViewModel.productObservable().subscribe { response ->
            productResponse = response
            initView()
        }
        updateProductViewModel.product(productId)
    }

    private fun initView() {
        etName.setText(productResponse.result!![0].name)
        etPrice.setText(productResponse.result!![0].price.toString())
        etDiscount.setText(productResponse.result!![0].discount.toString())
        etContent.setText(productResponse.result!![0].content)
        try {
            ivProductImage?.let {
                Glide.with(activity)
                    .asBitmap()
                    .load(productResponse.result?.get(0)?.image)
                    .into(it)
            }
        } catch (e: Exception) {
        }
        imageUrl = productResponse.result?.get(0)?.image.toString()
    }

    private fun getProductId() {
        productId = activity?.intent?.getStringExtra("ID") ?: ""
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateProductViewModel = UpdateProductViewModel(context)
    }
}