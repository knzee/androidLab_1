package io.pootis.androidlabtsu.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.pootis.androidlabtsu.R

class DetailsActivity : AppCompatActivity() {

    private val appTimePreferences = "time"
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        pref = getSharedPreferences(appTimePreferences, MODE_PRIVATE)

        val name = findViewById<TextView>(R.id.detailed_name)
        val img = findViewById<ImageView>(R.id.detailed_img)
        val desc = findViewById<TextView>(R.id.detailed_desc)

        name.text = intent.getStringExtra("name")
        desc.text = intent.getStringExtra("description")

        val url = intent.getStringExtra("imgUrl")
        Glide.with(this)
            .load(url)
            .into(img)
    }

    override fun onPause() {
        super.onPause()
        val editor = pref.edit()
        editor.putLong("currTime", System.currentTimeMillis())
        editor.apply()
        finish()
    }
}
