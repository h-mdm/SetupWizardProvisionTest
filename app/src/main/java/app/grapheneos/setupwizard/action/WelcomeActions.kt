package app.grapheneos.setupwizard.action

import android.app.Activity
import android.widget.Toast
import app.grapheneos.setupwizard.R

object WelcomeActions {
    private var qrToast: Toast? = null

    fun handleConsecutiveTap(welcomeTapCounter: Int, activity: Activity) {
        qrToast?.cancel()
        if (welcomeTapCounter >= 6) {
            // Start the QR code provisioning flow

        } else {
            if (welcomeTapCounter < 3) {
                return
            }
            val tapsRemaining = 6 - welcomeTapCounter
            val msg = activity.resources.getQuantityString(
                R.plurals.qr_provision_toast,
                tapsRemaining,
                Integer.valueOf(tapsRemaining)
            )
            qrToast = Toast.makeText(activity, msg, Toast.LENGTH_LONG)
            qrToast!!.show()
        }

    }
}
