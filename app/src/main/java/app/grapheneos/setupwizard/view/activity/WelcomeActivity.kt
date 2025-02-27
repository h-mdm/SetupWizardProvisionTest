package app.grapheneos.setupwizard.view.activity

import android.app.admin.DevicePolicyManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import app.grapheneos.setupwizard.R
import app.grapheneos.setupwizard.action.WelcomeActions
import app.grapheneos.setupwizard.android.ConsecutiveTapsGestureDetector
import app.grapheneos.setupwizard.android.ConsecutiveTapsGestureDetector.OnConsecutiveTapsListener
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
    }

    override fun onResume() {
        super.onResume()
        this.consecutiveTapsGestureDetector?.resetCounter()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val isTouchEventHandled = super.dispatchTouchEvent(ev)
        if (isTouchEventHandled) {
            this.consecutiveTapsGestureDetector?.resetCounter()
        } else {
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
