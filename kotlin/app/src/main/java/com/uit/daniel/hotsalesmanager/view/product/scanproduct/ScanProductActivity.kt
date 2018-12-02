package com.uit.daniel.hotsalesmanager.view.product.scanproduct

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.google.zxing.Result
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.view.product.productdetail.ProductDetailActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanProductActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)

        initView()
    }

    override fun handleResult(rawResult: Result?) {
        mScannerView?.resumeCameraPreview(this)
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("ID", rawResult?.text.toString())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    public override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    public override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }

}
