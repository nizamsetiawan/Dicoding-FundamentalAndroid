package com.example.connect.utils

import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog

object Loading {
    private var pDialog: SweetAlertDialog? = null
    fun showProgressDialog(context: Context) {
        hideProgressDialog()
        pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog?.progressHelper?.barColor = Color.parseColor("#A5DC86")
        pDialog?.titleText = "Memuat Data"
        pDialog?.setCancelable(false)
        pDialog?.show()
    }

    fun hideProgressDialog() {
        pDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
    fun showFailedDialog(context: Context, reloadAction: () -> Unit) {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Sorry...")
            .setContentText("Tidak ada internet yang terhubung")
            .setConfirmText("Reload")
            .setConfirmClickListener {
                reloadAction.invoke()
            }
            .show()
    }
}