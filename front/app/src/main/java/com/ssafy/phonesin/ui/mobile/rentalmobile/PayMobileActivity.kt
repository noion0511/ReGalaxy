package com.ssafy.phonesin.ui.mobile.rentalmobile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ActivityPayMobileBinding
import com.ssafy.phonesin.ui.mobile.ViewPagerAdapter


class PayMobileActivity : AppCompatActivity() {

    private var indicators: MutableList<ImageView> = mutableListOf()

    private lateinit var binding: ActivityPayMobileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayMobileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPayMobileUi()


    }

    private fun setPayMobileUi() {

        binding.buttonPayComplete.setOnClickListener {
            startActivity(Intent(this, FinishMobileActivity::class.java))
            finish()
        }


        val imageList = listOf(
            R.drawable.frame,
            R.drawable.frame,
            R.drawable.frame,
            // Add more images as needed
        )

        val adapter = ViewPagerAdapter(this, imageList)
        binding.viewPagerCardList.adapter = adapter

        // 뷰페이저의 페이지 변경 리스너를 설정합니다.
        binding.viewPagerCardList.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                updateIndicators(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        // 인디케이터를 만듭니다.
        createIndicators(imageList.size, 0)
    }

    private fun createIndicators(size: Int, currentPosition: Int) {
        indicators.clear()
        binding.layoutIndicator.removeAllViews()

        for (i in 0 until size) {
            val indicator = ImageView(this)
            val indicatorSize = resources.getDimensionPixelSize(R.dimen.indicator_size)
            val layoutParams = LinearLayout.LayoutParams(indicatorSize, indicatorSize)
            val margin = resources.getDimensionPixelSize(R.dimen.indicator_size)
            layoutParams.setMargins(margin, 0, margin, 0)
            indicator.layoutParams = layoutParams
            indicator.setImageResource(R.drawable.home)
            indicators.add(indicator)
            binding.layoutIndicator.addView(indicator)
        }

        updateIndicators(currentPosition)
    }

    private fun updateIndicators(currentPosition: Int) {
        for ((index, indicator) in indicators.withIndex()) {
            if (index == currentPosition) {
                indicator.setImageResource(R.drawable.select_home)
            } else {
                indicator.setImageResource(R.drawable.home)
            }
        }
    }
}