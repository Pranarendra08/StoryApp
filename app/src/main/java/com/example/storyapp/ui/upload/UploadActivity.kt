package com.example.storyapp.ui.upload

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityUploadBinding
import com.example.storyapp.helper.*
import com.example.storyapp.ui.main.MainActivity
import java.io.File

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding

    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private val uploadViewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.upload_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        currentPhotoPath = ""

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.buttonAdd.setOnClickListener {
            uploadImage()
        }

        uploadViewModel.isSuccess.observe(this) {
            uploadCheck(it)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadActivity,
                "com.example.storyapp.ui",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                rotateFile(file, true)
                getFile = file
                binding.ivPreviewImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@UploadActivity)
                getFile = myFile
                binding.ivPreviewImage.setImageURI(uri)
            }
        }
    }

    private fun uploadImage() {
        val description = binding.edAddDescription.text.toString()
        val getFile = getFile

        when {
            description.isEmpty() -> {
                binding.edAddDescription.error = getString(R.string.error_description_empty)
            }
            getFile == null -> {
                Toast.makeText(this, R.string.error_photo_empty, Toast.LENGTH_LONG).show()
            }
            else -> uploadViewModel.uploadStory(description, getFile)
        }
    }

    private fun uploadCheck(isSuccess: Boolean) {
        if (isSuccess) {
            startActivity(Intent(this@UploadActivity, MainActivity::class.java))
            finishAffinity()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}