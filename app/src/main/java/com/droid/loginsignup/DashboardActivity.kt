package com.droid.loginsignup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.droid.loginsignup.fragments.HomeFragment
import com.droid.loginsignup.fragments.AIFragment
import com.droid.loginsignup.fragments.CategoryFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardActivity : AppCompatActivity() {
    private var activeFragment: Fragment? = null
    private val homeFragment = HomeFragment()
    private val categoryFragment = CategoryFragment()
    private val aiFragment = AIFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        val floatingActionButton: FloatingActionButton = findViewById(R.id.fabBtn)

        // Add all fragments once and hide them except HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment, "HomeFragment")
                .add(R.id.fragment_container, categoryFragment, "CategoryFragment").hide(categoryFragment)
                .add(R.id.fragment_container, aiFragment, "AIFragment").hide(aiFragment)
                .commit()
            activeFragment = homeFragment
        } else {
            // Restore active fragment after configuration change
            activeFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_fragment -> switchFragment(homeFragment)
                R.id.categories_fragment -> switchFragment(categoryFragment)
            }
            true
        }

        floatingActionButton.setOnClickListener {
            switchFragment(aiFragment)
        }
    }

    private fun switchFragment(targetFragment: Fragment) {
        if (activeFragment == targetFragment) return

        supportFragmentManager.beginTransaction()
            .hide(activeFragment!!)
            .show(targetFragment)
            .commit()

        activeFragment = targetFragment
    }
}

