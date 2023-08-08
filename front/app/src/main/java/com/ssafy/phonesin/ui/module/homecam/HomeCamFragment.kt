package com.ssafy.phonesin.ui.module.homecam

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.pedro.rtplibrary.rtsp.RtspCamera1
import com.pedro.rtsp.utils.ConnectCheckerRtsp
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentHomeCamBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment

class HomeCamFragment : BaseFragment<FragmentHomeCamBinding>(R.layout.fragment_home_cam),
    ConnectCheckerRtsp {

    private lateinit var rtspCamera1: RtspCamera1

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeCamBinding {
        return FragmentHomeCamBinding.inflate(inflater, container, false)
    }

    override fun init() {
        if (checkPermissions()) {
        }

        initRtsp()
        bindingNonNull.startStreamButton.setOnClickListener { startStream() }
        bindingNonNull.pauseStreamButton.setOnClickListener { pauseStream() }
        bindingNonNull.stopStreamButton.setOnClickListener { stopStream() }
    }

    private fun initRtsp() {
        rtspCamera1 = RtspCamera1(bindingNonNull.surfaceView, this)
        rtspCamera1.prepareAudio()
        rtspCamera1.prepareVideo()
    }

    private fun startStream() {
        if (rtspCamera1.isStreaming) {
            showToast("Already Streaming")
            return
        }
        rtspCamera1.startStream("rtsp://your-server-address/your-stream-path")
        showToast("Stream Started")
    }

    private fun pauseStream() {
        if (rtspCamera1.isStreaming) {
            rtspCamera1.stopStream()
            showToast("Stream Paused")
        }
    }

    private fun stopStream() {
        if (rtspCamera1.isStreaming) {
            rtspCamera1.stopStream()
        }
        rtspCamera1.stopPreview()
        showToast("Stream Stopped")
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 1)
                return false
            }
        }

        return true
    }

    override fun onAuthErrorRtsp() {
        showToast("Auth Error")
    }

    override fun onAuthSuccessRtsp() {
        showToast("Auth Success")
    }

    override fun onConnectionFailedRtsp(reason: String) {
        showToast("Connection Failed: $reason")
        rtspCamera1.stopStream()
    }

    override fun onConnectionStartedRtsp(rtspUrl: String) {
        showToast("Connection Started: $rtspUrl")
    }

    override fun onConnectionSuccessRtsp() {
        showToast("Connection Success")
    }

    override fun onDisconnectRtsp() {
        showToast("Disconnected")
    }

    override fun onNewBitrateRtsp(bitrate: Long) {
        //비트레이트 처리가 필요한 경우 ?
    }
}
