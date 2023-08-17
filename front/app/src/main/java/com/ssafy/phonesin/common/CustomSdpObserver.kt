package com.ssafy.phonesin.common

import android.util.Log
import org.webrtc.SdpObserver
import org.webrtc.SessionDescription


open class CustomSdpObserver(tag: String) : SdpObserver {
    private val tag: String

    init {
        this.tag = "SdpObserver-$tag"
    }

    override fun onCreateSuccess(sdp: SessionDescription) {
        Log.d(tag, "onCreateSuccess, SDP: $sdp")
    }

    override fun onSetSuccess() {
        Log.d(tag, "onSetSuccess")
    }

    override fun onCreateFailure(error: String) {
        Log.e(tag, "onCreateFailure, error: $error")
    }

    override fun onSetFailure(error: String) {
        Log.e(tag, "onSetFailure, error: $error")
    }
}
