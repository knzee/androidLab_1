package io.pootis.androidlabtsu.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.pootis.androidlabtsu.R
import io.pootis.androidlabtsu.dagger.MyApplication
import io.pootis.androidlabtsu.data.GameViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val appTimePreferences = "time"
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        pref = getSharedPreferences(appTimePreferences, MODE_PRIVATE)
        editor = pref.edit()
        editor.putLong("currTime", 0)
        editor.apply()

        (application as MyApplication).appComponent.inject(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = GameListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val gameViewModel = ViewModelProvider(this,viewModelFactory).get(GameViewModel::class.java)
        gameViewModel.fetchGame()
        gameViewModel.games.observe(this, Observer { games ->
            games?.let { adapter.setGames(it) }
        })

    }

    override fun onPause() {
        super.onPause()
        editor = pref.edit()
        editor.putLong("currTime", System.currentTimeMillis())
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val prefTime : Long = pref.getLong("currTime", 0)
        if (System.currentTimeMillis() - prefTime > 5*60*1000 &&
               prefTime != 0L ) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.refresh -> {
            //gameViewModel.fetchGame()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
