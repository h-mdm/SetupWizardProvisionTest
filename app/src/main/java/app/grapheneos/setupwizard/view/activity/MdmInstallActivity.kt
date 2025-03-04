package app.grapheneos.setupwizard.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import app.grapheneos.setupwizard.R
import app.grapheneos.setupwizard.action.MdmInstallActions
import app.grapheneos.setupwizard.data.MdmInstallData


class MdmInstallActivity : AppCompatActivity() {
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
        MdmInstallActions.handleEntry(this);
    }

    /*override */fun onActivityResult(resultCode: Int, data: Intent?) {
        //super.onActivityResult(resultCode, data)
        MdmInstallActions.handleActivityResult(this, resultCode, data)
    }

    fun bindViews() {
        spinner = requireViewById(R.id.spinning_progress)
        message = requireViewById(R.id.text_message)
        linearProgress = requireViewById(R.id.linear_progress)
        progressLegend = requireViewById(R.id.progress_legend)
        secondaryButton.visibility = View.GONE
        primaryButton.visibility = View.GONE

        MdmInstallData.message.observe(this) {
            this.message.text = it
        }
        MdmInstallData.spinnerVisible.observe(this) {
            this.spinner.visibility = if (it) View.VISIBLE else View.GONE
        }
        MdmInstallData.progressVisible.observe(this) {
            val visibility = if (it) View.VISIBLE else View.GONE
            this.linearProgress.visibility = visibility
            this.progressLegend.visibility = visibility
        }
        MdmInstallData.downloadProgress.observe(this) {
            this.linearProgress.progress = it
        }
        MdmInstallData.downloadProgressLegend.observe(this) {
            this.progressLegend.text = it
        }
        MdmInstallData.error.observe(this) {
            if (it != null) {
                MdmInstallActions.handleError(this, it)
            }
        }
        MdmInstallData.complete.observe(this) {
            primaryButton.setText(R.string.next)
            primaryButton.visibility = View.VISIBLE
        }
    }

    @MainThread
    fun setupActions() {
        primaryButton.setOnClickListener {
            MdmInstallActions.provisionDeviceOwner(this)
        }
    }

}
