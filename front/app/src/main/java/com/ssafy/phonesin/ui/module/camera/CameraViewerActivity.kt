package com.ssafy.phonesin.ui.module.camera
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.viewpager.widget.ViewPager
import com.ssafy.phonesin.databinding.ActivityCameraViewerBinding
import me.relex.circleindicator.CircleIndicator
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraViewerBinding
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CameraPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photoPaths = intent.getStringArrayListExtra("photo_paths") ?: emptyList<String>()

        viewPager = binding.viewPagerPhotoViewer
        pagerAdapter = CameraPageAdapter(supportFragmentManager, photoPaths)
        viewPager.adapter = pagerAdapter

        val indicator: CircleIndicator = binding.indicator
        indicator.setViewPager(viewPager)

        binding.buttonPrintPicture.setOnClickListener {
            val currentImagePosition = viewPager.currentItem
            val currentImagePath = photoPaths[currentImagePosition]

            val intent = Intent(this, FrameActivity::class.java)
            intent.putExtra("imagePath", currentImagePath)
            startActivity(intent)
        }
    }
}
