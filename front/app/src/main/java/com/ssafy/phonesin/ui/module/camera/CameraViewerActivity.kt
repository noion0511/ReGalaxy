package com.ssafy.phonesin.ui.module.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityCameraViewerBinding

class CameraViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val photoPath = intent.getStringExtra("photo_path")

        if(photoPath != null) {
            showImage(photoPath)
        } else {
//            finish()
        }
    }

    private fun showImage(imagePath: String) {
        val bitmap = BitmapFactory.decodeFile(imagePath)

        val matrix = Matrix().apply {
            postRotate(90f)
            postScale(2f, 2f)
        }

        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        binding.photoViewer.setImageBitmap(rotatedBitmap)
    }

}
