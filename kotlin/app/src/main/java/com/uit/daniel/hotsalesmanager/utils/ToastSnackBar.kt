package com.uit.daniel.hotsalesmanager.utils

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView
import com.uit.daniel.hotsalesmanager.R

@SuppressLint("ValidFragment")
object ToastSnackBar : Fragment() {
    fun showSnackbar(content: String?, view: View, context: Context) {
        val snackbar = Snackbar.make(
            view, content.toString(),
            Snackbar.LENGTH_LONG
        ).setAction(context.getString(R.string.title_acctione), null)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(context.resources.getColor(R.color.colorAccent))
        val tvShowSnackbar =
            snackbarView.findViewById(android.support.design.R.id.snackbar_text) as TextView
        tvShowSnackbar.setTextColor(Color.WHITE)
        tvShowSnackbar.textSize = 14f
        snackbar.show()
    }
}