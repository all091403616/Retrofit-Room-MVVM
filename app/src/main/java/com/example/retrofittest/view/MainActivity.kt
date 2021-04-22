package com.example.retrofittest.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.retrofittest.R
import com.example.retrofittest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        navController=Navigation.findNavController(this,R.id.fragment)
        setSupportActionBar(binding.toolBar)
        val configurations= AppBarConfiguration(navController.graph,binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.toolBar,navController,configurations)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_,destination,_->
            if (destination.id==navController.graph.startDestination)
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            else
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
        binding.navView.setNavigationItemSelectedListener {
            if (it.itemId==R.id.aboutFragment){
                it.onNavDestinationSelected(navController)
            }else{
                binding.drawerLayout.closeDrawers()
                startActivity(Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT,"Simple app for Retrofit,Room and MVVM\nhttps://github.com/all091403616/Retrofit-Room-MVVM")
                    type="text/plain"
                })
            }

            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,binding.drawerLayout)
    }
}