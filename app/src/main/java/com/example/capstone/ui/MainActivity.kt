package com.example.capstone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.capstone.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseFirestore.setLoggingEnabled(true)
        FirebaseApp.initializeApp(this)
        navController = findNavController(R.id.nav_host_fragment)

        bottomNavMenu.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(
                        R.id.FirstFragment
                    )
                }
                R.id.navigation_search -> {
                    navController.navigate(
                        R.id.searchFragment
                    )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}