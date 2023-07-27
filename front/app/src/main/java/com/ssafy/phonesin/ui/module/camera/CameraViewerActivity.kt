package com.ssafy.phonesin.ui.module.camera
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

        viewPager = binding.viewPagerPhotoViewer
        pagerAdapter = CameraPageAdapter(supportFragmentManager, photoPaths)
        viewPager.adapter = pagerAdapter

        val indicator: CircleIndicator = binding.indicator
        indicator.setViewPager(viewPager)
    }
}
