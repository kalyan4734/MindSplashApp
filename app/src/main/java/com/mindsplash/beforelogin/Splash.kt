package com.mindsplash.beforelogin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.mindsplash.R
import com.mindsplash.afterlogin.common.HomeActivity
import com.mindsplash.helper.AppSharedPreference

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        var appSharedPreference: AppSharedPreference = AppSharedPreference.getMInstance()
        var sharedPreference: SharedPreferences = AppSharedPreference.getInstance(this)
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        var isMobilever: Boolean = appSharedPreference.isMobileVerified()
        var isRegistered: Boolean = appSharedPreference.isRegistered()
        if (!isMobilever && !isRegistered) {
            Handler().postDelayed({
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }, 3000) // 3000 is the delayed time in milliseconds.
        } else if (appSharedPreference.isMobileVerified && appSharedPreference.isRegistered) {
            Handler().postDelayed({
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000) // 3000 is the delayed time in milliseconds.
        } else if (appSharedPreference.isMobileVerified && !appSharedPreference.isRegistered) {
            Handler().postDelayed({
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("MOBNO", appSharedPreference.studentMobileNo)
                startActivity(intent)
                finish()
            }, 3000) // 3000 is the delayed time in milliseconds.
        }
    }
}