package app.grapheneos.setupwizard.view.activity

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import app.grapheneos.setupwizard.R
import app.grapheneos.setupwizard.action.ProvisioningActions
import app.grapheneos.setupwizard.action.SetupWizard
import app.grapheneos.setupwizard.action.WelcomeActions
import app.grapheneos.setupwizard.android.ConsecutiveTapsGestureDetector
import app.grapheneos.setupwizard.android.ConsecutiveTapsGestureDetector.OnConsecutiveTapsListener
import app.grapheneos.setupwizard.data.ProvisioningData
import app.grapheneos.setupwizard.data.WelcomeData
import com.google.zxing.integration.android.IntentIntegrator
import java.util.Locale


class ProvisioningActivity : AppCompatActivity() {
    private lateinit var primaryButton: Button
    private lateinit var secondaryButton: Button

    private lateinit var spinner: ProgressBar
    private lateinit var message: TextView
    private lateinit var linearProgress: ProgressBar
    private lateinit var progressLegend: TextView

    @Nullable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provisioning)
        primaryButton = requireViewById(R.id.primary_button)
        secondaryButton = requireViewById(R.id.secondary_button)
        bindViews()
        setupActions()
        ProvisioningActions.handleEntry(this);
    }

    /*override */fun onActivityResult(resultCode: Int, data: Intent?) {
        //super.onActivityResult(resultCode, data)
        ProvisioningActions.handleActivityResult(this, resultCode, data)
    }

    fun bindViews() {
        spinner = requireViewById(R.id.spinning_progress)
        message = requireViewById(R.id.text_message)
        linearProgress = requireViewById(R.id.linear_progress)
        progressLegend = requireViewById(R.id.progress_legend)
        secondaryButton.visibility = View.GONE
        primaryButton.visibility = View.GONE

        ProvisioningData.message.observe(this) {
            this.message.text = it
        }
        ProvisioningData.spinnerVisible.observe(this) {
            this.spinner.visibility = if (it) View.VISIBLE else View.GONE
        }
        ProvisioningData.progressVisible.observe(this) {
            val visibility = if (it) View.VISIBLE else View.GONE
            this.linearProgress.visibility = visibility
            this.progressLegend.visibility = visibility
        }
        ProvisioningData.downloadProgress.observe(this) {
            this.linearProgress.progress = it
        }
        ProvisioningData.downloadProgressLegend.observe(this) {
            this.progressLegend.text = it
        }
        ProvisioningData.error.observe(this) {
            if (it != null) {
                ProvisioningActions.handleError(this, it)
            }
        }
        ProvisioningData.complete.observe(this) {
            primaryButton.setText(R.string.next)
            primaryButton.visibility = View.VISIBLE
        }
    }

    @MainThread
    fun setupActions() {
        primaryButton.setOnClickListener {
            ProvisioningActions.provisionDeviceOwner(this)
        }
    }

}
