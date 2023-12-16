package edu.iu.alex.finalproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var menuIcon: ImageView
    private lateinit var searchIcon: ImageView
    private lateinit var newAccountText: TextView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    // Use the viewModels() delegate to get an instance of MainViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Initialize menu icon and set its click listener
        menuIcon = findViewById(R.id.menu_icon)
        menuIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        searchIcon = findViewById(R.id.search_icon)
        newAccountText = findViewById(R.id.tvNewAccount)

        // Inflate and add the sign out button footer to the NavigationView
        val navView: NavigationView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_sign_out -> {
                    FirebaseAuth.getInstance().signOut()
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_authFragment)
                }
                R.id.nav_home -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_homeFragment)
                }
                // Handle other menu items if needed
            }
            true
        }

        viewModel.isUserAuthenticated.observe(this) { isAuthenticated ->
            updateToolbarForUserStatus(isAuthenticated)
            if (isAuthenticated) {
                updateNavigationHeader()
            }
        }
    }


    private fun updateToolbarForUserStatus(isAuthenticated: Boolean) {
        if (isAuthenticated) {
            menuIcon.visibility = View.VISIBLE
            searchIcon.visibility = View.VISIBLE
            newAccountText.visibility = View.GONE
        } else {
            menuIcon.visibility = View.GONE
            searchIcon.visibility = View.GONE
            newAccountText.visibility = View.VISIBLE
        }
    }
    private fun updateNavigationHeader() {
        val headerView = navView.getHeaderView(0)
        val userEmailTextView: TextView = headerView.findViewById(R.id.nav_header_email)
        val userNameTextView: TextView = headerView.findViewById(R.id.nav_header_name)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val email = user.email ?: ""
            userEmailTextView.text = email

            val userName = email.substringBefore("@")
            userNameTextView.text = userName
        }
    }
}

