package io.pootis.androidlabtsu.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import io.pootis.androidlabtsu.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val appPreferences = "accounts"

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        pref = getSharedPreferences(appPreferences, MODE_PRIVATE)

        signUpButton.setOnClickListener {
            if (signUpLogin.text.toString() == "" || signUpPassword.text.toString() == "") {
                Toast.makeText(applicationContext,"Присутствуют пустые поля",Toast.LENGTH_SHORT).show()
            } else {
                val editor = pref.edit()
                editor.putString(signUpLogin.text.toString(), signUpPassword.text.toString())
                editor.apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                finish()
            }
        }
    }
}
