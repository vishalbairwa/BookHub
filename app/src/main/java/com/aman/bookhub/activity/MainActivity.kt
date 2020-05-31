package com.aman.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.aman.bookhub.*
import com.aman.bookhub.fragment.AboutAppFragment
import com.aman.bookhub.fragment.DashboardFragment
import com.aman.bookhub.fragment.FavouritesFragment
import com.aman.bookhub.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)

        setUpToolbar()

        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity, drawerLayout,
            R.string.open_drawer, R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isChecked = true
            it.isCheckable = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()

                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()

                }

                R.id.aboutApp -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutAppFragment())
                        .commit()

                    supportActionBar?.title = "About App"
                    drawerLayout.closeDrawers()
                }

                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouritesFragment())
                        .commit()


                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openDashboard() {
        val fragment = DashboardFragment()
        val transcation = supportFragmentManager.beginTransaction()
        transcation.replace(R.id.frame, fragment)
        transcation.commit()

        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when (frag) {
            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}
