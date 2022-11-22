package com.foodapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.databinding.ActivityHomeBinding
import com.foodapp.databinding.NavHeaderMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    private val viewModel by viewModels<HomeViewModel>()

    private val result = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.saveProfileUri(it.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.bottomNavView.setPadding(0, 0, 0, 0)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.cart, R.id.favorite),
            binding.drawerLayout
        )
        binding.bottomNavView.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.logout) {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            true
        }

        setUpActionBar()
        setSystemBarVisibility()
        setUpUserProfile()
    }

    private fun setSystemBarVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.orderItemFragment) {
                binding.bottomNavView.visibility = View.GONE
                binding.toolBar.visibility = View.GONE
            } else {
                binding.bottomNavView.visibility = View.VISIBLE
                binding.toolBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setUpUserProfile() {
        val headerView = binding.navView.getHeaderView(0)
        val navHeader = NavHeaderMainBinding.bind(headerView)
        navHeader.userName.text = auth.currentUser!!.displayName
        navHeader.userEmail.text = auth.currentUser!!.email

        navHeader.uploadProfile.setOnClickListener {
            result.launch("image/")
        }

        lifecycleScope.launch {
            viewModel.profileUri.collect {
                Glide.with(this@HomeActivity)
                    .load(it?.toUri())
                    .into(binding.profileImage)
                Glide.with(this@HomeActivity)
                    .load(it?.toUri())
                    .into(navHeader.uploadProfile)
            }
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
}