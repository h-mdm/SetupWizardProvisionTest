package app.grapheneos.setupwizard.action

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import app.grapheneos.setupwizard.R
import app.grapheneos.setupwizard.view.activity.MdmInstallActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

object WelcomeActions {
    private var qrToast: Toast? = null
    private var barcodeLauncher: ActivityResultLauncher<ScanOptions>? = null

    fun handleConsecutiveTap(welcomeTapCounter: Int, activity: AppCompatActivity) {
        qrToast?.cancel()
        if (welcomeTapCounter >= 6) {
            startQrProvisioning();
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

    fun initQrProvisioning(activity: AppCompatActivity) {
        barcodeLauncher = activity.registerForActivityResult(
            ScanContract()
        ) { result ->
            if (result.contents == null) {
                Toast.makeText(activity, R.string.qr_provisioning_cancelled, Toast.LENGTH_LONG).show()
            } else {
                launchQrProvisioning(activity, result.contents)
            }
        }
    }

    fun startQrProvisioning() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setBeepEnabled(false)
        barcodeLauncher?.launch(options)
    }

    fun launchQrProvisioning(activity: AppCompatActivity, contents: String) {
        val intent = Intent(activity, MdmInstallActivity::class.java)
        intent.putExtra(MdmInstallActions.EXTRA_QR_CONTENTS, contents)
        SetupWizard.startActivity(activity, intent)
    }
}
