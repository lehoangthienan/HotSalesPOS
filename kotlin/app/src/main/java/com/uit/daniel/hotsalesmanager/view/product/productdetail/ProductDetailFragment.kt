package com.uit.daniel.hotsalesmanager.view.product.productdetail

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import com.uit.daniel.hotsalesmanager.view.order.orderproduct.OrderProductActivity
import com.uit.daniel.hotsalesmanager.view.product.productlocation.ProductLocationActivity
import kotlinx.android.synthetic.main.fragment_product_detail.*
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder


class ProductDetailFragment : Fragment() {

    private val priceUtils = PriceUtils()
    private var productId: String = ""
    private lateinit var productDetailViewModel: ProductDetailViewModel
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var multiFormatWriter = MultiFormatWriter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductId()
        getProductDetail()
        addEvents()
    }

    private fun getProductId() {
        productId = activity?.intent?.getStringExtra("ID") ?: ""

        initQRCODE()
    }

    private fun initQRCODE() {
        try {
            val bitMatrix: BitMatrix? = multiFormatWriter.encode(productId, BarcodeFormat.QR_CODE, 350, 350)

            val barcodeEncoder = BarcodeEncoder()
            val bm = barcodeEncoder.createBitmap(bitMatrix)

            if(bm != null) {
                ivQRCODE.setImageBitmap(bm)
            }
        } catch (e: WriterException){}
    }

    @SuppressLint("CheckResult")
    private fun getProductDetail() {
        productDetailViewModel.productObservable().subscribe { productResponse ->
            initView(productResponse)
        }
        productDetailViewModel.product(productId)
    }

    @SuppressLint("SetTextI18n")
    private fun initView(productResponse: ProductResponse) {

        lat = productResponse.result?.get(0)?.lat!!
        lng = productResponse.result?.get(0)?.lng!!

        tvProductName.text = productResponse.result?.get(0)?.name
        try {
            ivProduct?.let {
                Glide.with(activity)
                    .asBitmap()
                    .load(productResponse.result?.get(0)?.image)
                    .into(it)
            }
        } catch (e: Exception) {
        }
        tvPrice.text = productResponse.result?.get(0)?.price?.let { priceUtils.setStringMoney(it) }
        tvPriceDiscount.text = priceUtils.setStringMoney(productResponse.result?.get(0)?.discount?.let {
            productResponse.result?.get(0)?.price?.let { it1 ->
                priceUtils.priceDiscount(
                    it,
                    it1
                )
            }
        }!!)
        tvPercentDiscount.text = productResponse.result?.get(0)?.discount.toString() + "%"
        tvBranchName.text = productResponse.result?.get(0)?.owner?.name
        tvContent.text = productResponse.result?.get(0)?.content
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        btOrder.setOnClickListener {
            startOrderActivity()
        }
        ivLocation.setOnClickListener {
            startProductLocationActivity()
        }
        ivDirection.setOnClickListener {
            if (lat != 0.0 || lng != 0.0) {
                val gmmIntentUri = Uri.parse("google.navigation:q=$lat,$lng")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else ToastSnackBar.showSnackbar("The product you just selected has no location", view, activity)
        }
    }

    private fun startProductLocationActivity() {
        if (lat != 0.0 || lng != 0.0) {
            val intent = Intent(activity, ProductLocationActivity::class.java)
            intent.putExtra("LAT", lat)
            intent.putExtra("LNG", lng)
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        } else ToastSnackBar.showSnackbar("The product you just selected has no location", view, activity)
    }

    private fun startOrderActivity() {
        val intent = Intent(activity, OrderProductActivity::class.java)
        intent.putExtra("ID", productId)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        productDetailViewModel = ProductDetailViewModel(context)
    }

}