package id.riverflows.moviicatexp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import id.riverflows.moviicatexp.R

object Utils {

    fun showIndefiniteSnackBar(view: View, message: String, anchor: View? = null){
        val context = view.context
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).apply {
            anchorView = anchor
        }
        snackBar.setAction(context.getString(R.string.action_close)){
            snackBar.dismiss()
        }
        snackBar.show()
    }
}