package com.uit.daniel.hotsalesmanager.view.order.orderuser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import com.uit.daniel.hotsalesmanager.R

class OrderUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_user)

        initView()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }
}
