package com.example.monogram.ui

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.monogram.R
import com.example.monogram.User
import com.example.monogram.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var user: User
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var userObserver: Observer<User>
    private val args: SettingsFragmentArgs by navArgs()
    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val selectedImage = result.data?.data!!
                val source = ImageDecoder.createSource(context?.contentResolver!!, selectedImage)
                val bitmap = ImageDecoder.decodeBitmap(source)
                user.photoBitmapLocal = bitmap
                user.update()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = args.user

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.owner_context, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }

    override fun onStart() {
        super.onStart()

        binding = FragmentSettingsBinding.bind(rootView)
        val userWithoutPhoto = ResourcesCompat.getDrawable(
            resources, R.drawable.baseline_person_24, resources.newTheme()
        )

        if (user.name.isEmpty()) {
            binding.avatar.setImageDrawable(userWithoutPhoto)
        }

        userObserver = Observer {
            binding.avatar.setImageBitmap(it.photoBitmapLocal)
        }
        setListeners()
    }

    private fun setListeners() = with(binding) {
        user.observe(viewLifecycleOwner, userObserver)

        newPhoto.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryResultLauncher.launch(galleryIntent)
        }
    }

    override fun onStop() {
        super.onStop()
        user.removeObserver(userObserver)
    }
}