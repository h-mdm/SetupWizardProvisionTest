package app.grapheneos.setupwizard.view.activity

import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import app.grapheneos.setupwizard.R
import app.grapheneos.setupwizard.action.WelcomeActions
import app.grapheneos.setupwizard.android.ConsecutiveTapsGestureDetector
import app.grapheneos.setupwizard.android.ConsecutiveTapsGestureDetector.OnConsecutiveTapsListener
import com.google.zxing.integration.android.IntentIntegrator
import java.util.Locale


class WelcomeActivity : AppCompatActivity() {
    @Nullable
    private var consecutiveTapsGestureDetector: ConsecutiveTapsGestureDetector? = null
    @Nullable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome);

        consecutiveTapsGestureDetector = ConsecutiveTapsGestureDetector(
            this.onConsecutiveTapsListener,
            findViewById<View>(R.id.glif_layout)
        )

        WelcomeActions.initQrProvisioning(this);
    }

    override fun onResume() {
        super.onResume()
        this.consecutiveTapsGestureDetector?.resetCounter()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val isTouchEventHandled = super.dispatchTouchEvent(ev)
        Log.d("WelcomeActivity", "dispatchTagEvent action: " + ev.action)
        if (ev.action == MotionEvent.ACTION_UP) {
            this.consecutiveTapsGestureDetector?.onTouchEvent(ev)
        }
        return isTouchEventHandled
    }

    private val onConsecutiveTapsListener: OnConsecutiveTapsListener =
        object : OnConsecutiveTapsListener {
            override fun onConsecutiveTaps(welcomeTapCounter: Int) {
                WelcomeActions.handleConsecutiveTap(welcomeTapCounter, this@WelcomeActivity)
            }
        }

}
