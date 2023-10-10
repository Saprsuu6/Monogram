package com.example.monogram

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.monogram.databinding.ActivityMainBinding
import com.example.monogram.databinding.NavHeaderMainBinding
import com.example.monogram.ui.chats.ChatsFragmentDirections
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding
    private lateinit var navController: NavController
    private val user = User()
    private var isOpenContextOwner = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        navHeaderMainBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        hideSystemUi()
        initNavigation()

        setDefaultInfo()
        setSideNavigationListeners()
        ownerContextMenu()
        changeTheme()
    }

    private fun setSideNavigationListeners() {
        binding.navView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.nav_create_group -> {
                    Toast.makeText(this, "create_group group", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_secret_chat -> {
                    Toast.makeText(this, "secret_chat", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_create_channel -> {
                    Toast.makeText(this, "create_channel", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_contacts -> {
                    Toast.makeText(this, "contacts group", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_saves -> {
                    Toast.makeText(this, "saves", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_settings -> {
                    //supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    val action = ChatsFragmentDirections.actionChatsToNavSettings(user)
                    navController.navigate(action)
                    true
                }

                R.id.nav_invite_friends -> {
                    Toast.makeText(this, "invite_friends", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_questions -> {
                    Toast.makeText(this, "questions", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    Toast.makeText(this, "else", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
    }

    private fun setDefaultInfo() {
        val userWithoutPhoto = ResourcesCompat.getDrawable(
            resources, R.drawable.baseline_person_24, theme
        )

        if (user.photoLink.isEmpty()) {
            navHeaderMainBinding.avatar.setImageDrawable(userWithoutPhoto)
        }
    }

    private fun changeTheme() {
        navHeaderMainBinding.theme.setOnClickListener {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                }

                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                }
            }
        }
    }

    private fun initNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.chats, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun ownerContextMenu() {
        val dropUp = ResourcesCompat.getDrawable(
            resources, R.drawable.baseline_arrow_drop_up_24, theme
        )
        val dropDown = ResourcesCompat.getDrawable(
            resources, R.drawable.baseline_arrow_drop_down_24, theme
        )

        navHeaderMainBinding.contextMenu.setOnClickListener {
            if (!isOpenContextOwner) {
                (it as ImageView).setImageDrawable(dropUp)
                binding.navView.menu[0].isVisible = true
            } else {
                (it as ImageView).setImageDrawable(dropDown)
                binding.navView.menu[0].isVisible = false
            }

            isOpenContextOwner = !isOpenContextOwner
        }
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}