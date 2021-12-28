package com.mindsplash.beforelogin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.mindsplash.R
import com.mindsplash.afterlogin.common.HomeActivity
import com.mindsplash.helper.*
import com.mindsplash.network.model.ErrorResponse
import com.mindsplash.network.model.Student
import com.mindsplash.network.model.VerifyMobile
import com.mindsplash.services.auth_reg.MobileVerifyService
import com.mindsplash.services.auth_reg.auth_reg_impl.MobileVerifyImpl

class Login : AppCompatActivity() {
    lateinit var verifedButton: MaterialButton
    lateinit var edtMobileNumbe: TextView
    lateinit var connectionDetector: ConnectionDetector
    lateinit var progressDialog: Dialog

    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        verifedButton = findViewById(R.id.verifyLogin)
        edtMobileNumbe = findViewById(R.id.MobileNo_EditText)
        connectionDetector = ConnectionDetector(this)
        progressDialog = AppUtils().getProgressDialog(this)
        verifedButton.setOnClickListener(View.OnClickListener {
            if (validate())
                if (connectionDetector.isConnectingToInternet)
                    callMobileOtp()
                else
                    AppUtils().showError(
                        this,
                        resources.getString(R.string.check_inter_connection)
                    )
        })
        onChangeMobileNumber();
    }

    private fun onChangeMobileNumber() {
        edtMobileNumbe.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length == 10)
                    verifedButton.background = resources.getDrawable(R.drawable.button_shape)
                else
                    verifedButton.background = resources.getDrawable(R.drawable.gray_rounded_button)
            }
        })

    }

    fun callMobileOtp() {
        progressDialog.show()
        var mobileVerifyService: MobileVerifyService = MobileVerifyImpl()

        mobileVerifyService.verifyMobile(edtMobileNumbe.text.toString(), object : CallBack() {
            override fun onSuccess(`object`: Any?) {
                progressDialog.dismiss()
                if (`object` != null) {
                    try {
                        var verifyMobile = `object` as VerifyMobile
                        var intent = Intent(applicationContext, OtpVerification::class.java)
                        intent.putExtra("MOBNO", edtMobileNumbe.text.toString())
                        startActivity(intent)
                    } catch (e: Exception) {
                        var errorResponse = `object` as ErrorResponse
                        //AppUtils.showSuccess(this@Login,errorResponse.message)
                        Handler().postDelayed(Runnable {
                            val appSharedPreference = AppSharedPreference.getMInstance()
                            AppSharedPreference.getInstance(this@Login)
                            appSharedPreference.addStudentMobileNo(errorResponse.drivdata.mobileno)
                            appSharedPreference.isMobileVerified = true
                            val student: Student = errorResponse.drivdata
                            if (student.firstname==null || student.lastname==null ) {
                                val intent = Intent(applicationContext, RegisterActivity::class.java)
                                intent.putExtra("MOBNO", edtMobileNumbe.text.toString().trim())
                                startActivity(intent)
                            } else {
                                appSharedPreference.isRegistered = true
                                val intent = Intent(applicationContext, HomeActivity::class.java)
                                intent.putExtra("MOBNO", edtMobileNumbe.text.toString().trim())
                                startActivity(intent)
                            }
                        },1000)
                    }
                }
            }

            override fun onError(`object`: Any?) {
                progressDialog.dismiss()
                AppUtils().showError(this@Login, `object`.toString())
            }
        })
    }

    fun validate(): Boolean {
        var flag: Boolean
        var mobileNumber = edtMobileNumbe.text.toString().trim()
        if (mobileNumber.length == 0) {
            edtMobileNumbe.error = "Enter your mobile number"
            flag = false
        } else if (mobileNumber.length < 10) {
            edtMobileNumbe.error = "Enter valid mobile number"
            flag = false
        } else
            flag = true
        return flag
    }

}