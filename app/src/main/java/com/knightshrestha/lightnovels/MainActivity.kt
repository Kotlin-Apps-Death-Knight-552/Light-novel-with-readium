package com.knightshrestha.lightnovels

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.knightshrestha.lightnovels.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 123 // You can choose any request code
    }

    private lateinit var binding: ActivityMainBinding
    var navView: BottomNavigationView? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Your activity initialization code here

        // Check if the permission is already granted
        if (Environment.isExternalStorageManager()) {
            Log.d("permit", "is manager")
            loadApp()
        } else {
            // Permission not granted, request it
            Log.d("permit", "is not manager")

            requestPermission()
        }
    }

    private fun askManagerPermit() {
        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
//        intent.data = Uri.parse("package:$application.packageName")
        startActivity(intent)
    }

    private fun requestPermission() {
        if (Environment.isExternalStorageManager()) {
            Log.d("permit", "is manager")
            loadApp()
        } else {
            // Permission not granted, request it
            Log.d("permit", "is not manager")
            askManagerPermit()
//            requestPermission()
        }
    }

    private fun loadApp() {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_import,
                R.id.navigation_bookmarks,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView!!.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}

