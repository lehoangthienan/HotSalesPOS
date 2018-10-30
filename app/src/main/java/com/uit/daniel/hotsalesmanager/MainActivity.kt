package com.uit.daniel.hotsalesmanager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setTimeSleep()
    }

    @SuppressLint("CheckResult")
    private fun setTimeSleep() {
        val handler = Handler()
        handler.postDelayed({
            startSalesManagerActivity()
        }, 2000)
    }

    private fun startSalesManagerActivity() {
        val intent = Intent(this, SalesManagerActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }
}
