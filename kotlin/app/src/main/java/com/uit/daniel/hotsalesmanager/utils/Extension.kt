package com.uit.daniel.hotsalesmanager.utils

import android.view.View

fun getVisibilityView(isValid: Boolean): Int {
    if (isValid)
        return View.VISIBLE
    return View.GONE
}

fun getVisibilityAlarmContent(visible: Boolean, isTextEmpty: Boolean): Int {
    if (visible && !isTextEmpty) return View.VISIBLE
    return View.GONE
}

fun getVisibilityTimePlanEditGroup(isValid: Boolean): Int {
    if (isValid)
        return View.VISIBLE
    return View.INVISIBLE
}