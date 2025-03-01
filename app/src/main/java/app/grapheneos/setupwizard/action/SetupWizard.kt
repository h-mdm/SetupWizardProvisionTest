package app.grapheneos.setupwizard.action

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent

object SetupWizard {
    fun startActivity(context: Activity, intent: Intent) {
        context.startActivity(intent)
    }
}