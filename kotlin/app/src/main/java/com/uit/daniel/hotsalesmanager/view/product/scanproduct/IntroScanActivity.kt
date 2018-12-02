package com.uit.daniel.hotsalesmanager.view.product.scanproduct

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.uit.daniel.hotsalesmanager.R
import kotlinx.android.synthetic.main.activity_intro_scan.*

class IntroScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_scan)

        initView()
        addeEvents()
    }

    private fun addeEvents() {
        btScan.setOnClickListener {
            val intent = Intent(this, ScanProductActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
