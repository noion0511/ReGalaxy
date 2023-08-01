package com.ssafy.phonesin.ui.mobile.rentalmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentRentalPayBinding
import com.ssafy.phonesin.ui.mobile.ViewPagerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RentalPayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentalPayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var indicators: MutableList<ImageView> = mutableListOf()

    private lateinit var binding: FragmentRentalPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRentalPayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPayUi()
    }

    private fun setPayUi() {

        binding.buttonPayComplete.setOnClickListener {
        }

        //TODO : imageList를 viewmodel에 추가해야한다.
        val imageList = listOf(
            R.drawable.frame,
            R.drawable.frame,
            R.drawable.frame,
            // Add more images as needed
        )
        val adapter = ViewPagerAdapter(requireContext(), imageList)
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
            val indicator = ImageView(context)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RentalPayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RentalPayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}