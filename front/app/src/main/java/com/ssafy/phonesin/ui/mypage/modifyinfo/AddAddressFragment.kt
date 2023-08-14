package com.ssafy.phonesin.ui.mypage.modifyinfo

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageAddAddressBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.mypage.UserViewModel
import com.ssafy.phonesin.ui.util.setDebouncingClickListener


class AddAddressFragment : Fragment() {
    private lateinit var binding: FragmentMyPageAddAddressBinding
    private lateinit var searchDialog: Dialog

    val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageAddAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() = with(binding) {
        editTextRoadAddress.isFocusableInTouchMode = false

        editTextRoadAddress.setDebouncingClickListener {
            val status: Int = NetworkStatus.getConnectivityStatus(requireContext())
            if (status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                showRoadAddressDialog()
            } else {
                Toast.makeText(requireContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonSaveNewAddress.setDebouncingClickListener {
            val newAddress = "${editTextRoadAddress.text} ${editTextDetailAddress.text}"
            Log.d("buttonSaveNewAddress", "setOnClick: $newAddress")
            userViewModel.postAddress(newAddress)
            findNavController().navigate(R.id.modifyInfoFragment)
        }
    }

    private fun showRoadAddressDialog() {
        searchDialog = Dialog(requireContext())

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        searchDialog.setContentView(R.layout.dialog_my_road_address_search)
        searchDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val cornerRadius = resources.getDimension(R.dimen.webview_dialog_corner_radius)
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius
        shape.setColor(Color.WHITE)
        searchDialog.window?.setBackgroundDrawable(shape)

        val webView = searchDialog.findViewById<WebView>(R.id.webViewRoadAddressSearch)

        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(MyJavaScriptInterface(), "Android")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                webView.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }

        webView.loadUrl("http://i9d102.p.ssafy.io/address.html")

        searchDialog.show()

    }

    inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            searchDialog.dismiss()
            binding.editTextRoadAddress.setText(data)
        }
    }
}