package com.ssafy.phonesin.common.openvidu

import android.content.Context
import android.os.Build
import org.webrtc.Camera1Enumerator
import org.webrtc.Camera2Enumerator
import org.webrtc.CameraEnumerator
import org.webrtc.EglBase
import org.webrtc.IceCandidate
import org.webrtc.MediaConstraints
import org.webrtc.PeerConnectionFactory
import org.webrtc.SessionDescription
import org.webrtc.SurfaceTextureHelper
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoCapturer


class LocalParticipant(
    participantName: String?,
    session: Session,
    private val context: Context,
    private val localVideoView: SurfaceViewRenderer
) :
    Participant(participantName.toString(), session) {
    private var surfaceTextureHelper: SurfaceTextureHelper? = null
    private var videoCapturer: VideoCapturer? = null
    private val localIceCandidates: MutableCollection<IceCandidate>
    var localSessionDescription: SessionDescription? = null
        private set

    init {
        localIceCandidates = ArrayList()
        session.localParticipant = this

    }

    fun startCamera() {
        val eglBaseContext = EglBase.create().eglBaseContext
        val peerConnectionFactory: PeerConnectionFactory? = this.session.peerConnectionFactory

        // Create AudioSource
        val audioSource = peerConnectionFactory?.createAudioSource(MediaConstraints())
        if (peerConnectionFactory != null) {
            this.audioTrack = peerConnectionFactory.createAudioTrack("101", audioSource)
        }
        surfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", eglBaseContext)

        // Create VideoCapturer
        val videoCapturer = createCameraCapturer()
        val videoSource = peerConnectionFactory?.createVideoSource(
            videoCapturer!!.isScreencast
        )
        if (videoCapturer != null) {
            if (videoSource != null) {
                videoCapturer.initialize(surfaceTextureHelper, context, videoSource.capturerObserver)
            }
        }
        if (videoCapturer != null) {
            videoCapturer.startCapture(480, 640, 30)
        }

        // Create VideoTrack
        if (peerConnectionFactory != null) {
            this.videoTrack = peerConnectionFactory.createVideoTrack("100", videoSource)
        }

        // Display in localView
        this.videoTrack?.addSink(localVideoView)
    }

    private fun createCameraCapturer(): VideoCapturer? {
        val enumerator: CameraEnumerator
        enumerator = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Camera2Enumerator(context)
        } else {
            Camera1Enumerator(false)
        }
        val deviceNames = enumerator.deviceNames

        // Try to find front facing camera
        for (deviceName in deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                videoCapturer = enumerator.createCapturer(deviceName, null)
                if (videoCapturer != null) {
                    return videoCapturer
                }
            }
        }
        // Front facing camera not found, try something else
        for (deviceName in deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                videoCapturer = enumerator.createCapturer(deviceName, null)
                if (videoCapturer != null) {
                    return videoCapturer
                }
            }
        }
        return null
    }

    fun storeIceCandidate(iceCandidate: IceCandidate) {
        localIceCandidates.add(iceCandidate)
    }

    fun getLocalIceCandidates(): Collection<IceCandidate> {
        return localIceCandidates
    }

    fun storeLocalSessionDescription(sessionDescription: SessionDescription?) {
        localSessionDescription = sessionDescription
    }

    override fun dispose() {
        super.dispose()
        if (videoTrack != null) {
            videoTrack!!.removeSink(localVideoView)
            videoCapturer!!.dispose()
            videoCapturer = null
        }
        if (surfaceTextureHelper != null) {
            surfaceTextureHelper!!.dispose()
            surfaceTextureHelper = null
        }
    }
}
