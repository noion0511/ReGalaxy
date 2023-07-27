package com.ssafy.phonesin.ui.module.camera
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ssafy.phonesin.databinding.ActivityCameraViewerBinding
import me.relex.circleindicator.CircleIndicator

class CameraViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraViewerBinding
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CameraPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val photoPaths = intent.getStringArrayListExtra("photo_paths") ?: emptyList<String>()
        val cameraFacing = intent.getStringArrayListExtra("cameraFacing") ?: emptyList<String>()

        viewPager = binding.viewPagerPhotoViewer
        pagerAdapter = CameraPageAdapter(supportFragmentManager, photoPaths, cameraFacing)
        viewPager.adapter = pagerAdapter

        val indicator: CircleIndicator = binding.indicator
        indicator.setViewPager(viewPager)

        binding.buttonPrintPicture.setOnClickListener {
            val currentImagePosition = viewPager.currentItem
            val currentImagePath = photoPaths[currentImagePosition]
            val currentCameraFace = cameraFacing[currentImagePosition]

            val intent = Intent(this, FrameActivity::class.java)
            intent.putExtra("imagePath", currentImagePath)
            intent.putExtra("cameraFacing", currentCameraFace)
            startActivity(intent)
        }
    }
}
