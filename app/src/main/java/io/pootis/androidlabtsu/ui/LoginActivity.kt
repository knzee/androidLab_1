package io.pootis.androidlabtsu.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import io.pootis.androidlabtsu.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.Executors

class LoginActivity : AppCompatActivity() {

    private val appPreferences = "accounts"

    private lateinit var pref: SharedPreferences

    private val TAG = LoginActivity::getLocalClassName.toString()

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var biometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup.setOnClickListener {
            goToSignUpActivity()
        }

        pref = getSharedPreferences(appPreferences, MODE_PRIVATE)

        signin.setOnClickListener {
            Handler().postDelayed({

                if (pref.getString(login.text.toString(), "") != "") {
                    goToMainActivity()
                } else {
                    showToast("Неверное имя пользователя или пароль")
                }

            }, 150)

        }

        forgot.setOnClickListener {
            pref.edit().remove(login.text.toString()).apply()
            showToast("Профиль " + login.text.toString() + " удалён")
            goToSignUpActivity()
        }

        biometricManager = BiometricManager.from(this)
        val executor = ContextCompat.getMainExecutor(this)

        checkBiometricStatus(biometricManager)

        biometricPrompt = BiometricPrompt(this,executor,
            object: BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("Authentication error $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    goToMainActivity()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Authentication failed")
                }
            }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setDescription("PALCHIK PRILOJI")
            .setNegativeButtonText("Standart authentication")
            .build()

        button.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }

    private fun showToast(message : String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    private fun goToMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToSignUpActivity() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun checkBiometricStatus(biometricManager: BiometricManager) {
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS->
                Log.d(TAG,"BM_Status: Ready")

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->
                Log.e(TAG,"BM_Status: no biometric features available")

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->
                Log.e(TAG,"BM_Status: biometric features are not available")

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->
                Log.e(TAG,"BM_Status: fingerprint is not registered in this device")
        }
    }
}
