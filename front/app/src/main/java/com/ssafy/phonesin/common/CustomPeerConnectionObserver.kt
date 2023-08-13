package com.ssafy.phonesin.common

import android.util.Log
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceConnectionState
import org.webrtc.PeerConnection.IceGatheringState
import org.webrtc.PeerConnection.SignalingState
import org.webrtc.RtpReceiver
import java.util.Arrays


open class CustomPeerConnectionObserver(id: String) : PeerConnection.Observer {
    private var TAG = "PeerConnection"

    init {
        TAG = TAG + "-" + id
    }

    override fun onSignalingChange(signalingState: SignalingState) {
        Log.d(
            TAG,
            "onSignalingChange() called with: signalingState = [$signalingState]"
        )
    }

    override fun onIceConnectionChange(iceConnectionState: IceConnectionState) {
        Log.d(
            TAG,
            "onIceConnectionChange() called with: iceConnectionState = [$iceConnectionState]"
        )
    }

    override fun onIceConnectionReceivingChange(b: Boolean) {
        Log.d(TAG, "onIceConnectionReceivingChange() called with: b = [$b]")
    }

    override fun onIceGatheringChange(iceGatheringState: IceGatheringState) {
        Log.d(
            TAG,
            "onIceGatheringChange() called with: iceGatheringState = [$iceGatheringState]"
        )
    }

    override fun onIceCandidate(iceCandidate: IceCandidate) {
        Log.d(TAG, "onIceCandidate() called with: iceCandidate = [$iceCandidate]")
    }

    override fun onIceCandidatesRemoved(iceCandidates: Array<IceCandidate>) {
        Log.d(
            TAG,
            "onIceCandidatesRemoved() called with: iceCandidates = [" + Arrays.toString(
                iceCandidates
            ) + "]"
        )
    }

    override fun onAddStream(mediaStream: MediaStream) {
        Log.d(TAG, "onAddStream() called with: mediaStream = [$mediaStream]")
    }

    override fun onRemoveStream(mediaStream: MediaStream) {
        Log.d(TAG, "onRemoveStream() called with: mediaStream = [$mediaStream]")
    }

    override fun onDataChannel(dataChannel: DataChannel) {
        Log.d(TAG, "onDataChannel() called with: dataChannel = [$dataChannel]")
    }

    override fun onRenegotiationNeeded() {
        Log.d(TAG, "onRenegotiationNeeded() called")
    }

    override fun onAddTrack(rtpReceiver: RtpReceiver, mediaStreams: Array<MediaStream>) {
        Log.d(
            TAG,
            "onAddTrack() called with: mediaStreams = [" + Arrays.toString(mediaStreams) + "]"
        )
    }
}
