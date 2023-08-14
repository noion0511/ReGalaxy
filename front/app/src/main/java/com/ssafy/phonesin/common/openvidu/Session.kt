package com.ssafy.phonesin.common.openvidu

import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.common.CustomPeerConnectionObserver
import com.ssafy.phonesin.common.CustomSdpObserver
import com.ssafy.phonesin.common.utils.setRemoteMediaStream
import com.ssafy.phonesin.common.websocket.CustomWebSocket
import org.webrtc.IceCandidate
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.MediaStreamTrack
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceServer
import org.webrtc.PeerConnection.RTCConfiguration
import org.webrtc.PeerConnection.SignalingState
import org.webrtc.PeerConnectionFactory
import org.webrtc.PeerConnectionFactory.InitializationOptions
import org.webrtc.RtpReceiver
import org.webrtc.RtpTransceiver
import org.webrtc.RtpTransceiver.RtpTransceiverInit
import org.webrtc.SessionDescription
import org.webrtc.SoftwareVideoDecoderFactory
import org.webrtc.SoftwareVideoEncoderFactory
import org.webrtc.VideoDecoderFactory
import org.webrtc.VideoEncoderFactory
import java.util.Arrays


class Session(
    val id: String,
    val token: String,
    private val views_container: LinearLayout,
    activity: AppCompatActivity
) {
    var localParticipant: LocalParticipant? = null
    private val remoteParticipants: MutableMap<String, RemoteParticipant> =
        HashMap<String, RemoteParticipant>()
    private val iceServersDefault =
        Arrays.asList(IceServer.builder("stun:stun.l.google.com:19302").createIceServer())
    private var iceServers: List<IceServer?> = ArrayList<IceServer?>()
    var peerConnectionFactory: PeerConnectionFactory?
        private set
    private var websocket: CustomWebSocket? = null
    private val activity: AppCompatActivity

    init {
        this.activity = activity

        // Creating a new PeerConnectionFactory instance
        val optionsBuilder = InitializationOptions.builder(activity.getApplicationContext())
        optionsBuilder.setEnableInternalTracer(true)
        val opt = optionsBuilder.createInitializationOptions()
        PeerConnectionFactory.initialize(opt)
        val options = PeerConnectionFactory.Options()

        // Using software encoder and decoder
        val encoderFactory: VideoEncoderFactory
        val decoderFactory: VideoDecoderFactory
        encoderFactory = SoftwareVideoEncoderFactory()
        decoderFactory = SoftwareVideoDecoderFactory()
        peerConnectionFactory = PeerConnectionFactory.builder()
            .setVideoEncoderFactory(encoderFactory)
            .setVideoDecoderFactory(decoderFactory)
            .setOptions(options)
            .createPeerConnectionFactory()
    }

    fun setWebSocket(websocket: CustomWebSocket?) {
        this.websocket = websocket
    }

    fun createLocalPeerConnection(): PeerConnection? {
        val config = RTCConfiguration(if (iceServers.isEmpty()) iceServersDefault else iceServers)
        config.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.ENABLED
        config.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE
        config.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.NEGOTIATE
        config.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY
        config.keyType = PeerConnection.KeyType.ECDSA
        config.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN
        val peerConnection = peerConnectionFactory!!.createPeerConnection(
            config,
            object : CustomPeerConnectionObserver("local") {
                override fun onIceCandidate(iceCandidate: IceCandidate) {
                    super.onIceCandidate(iceCandidate)
                    websocket!!.onIceCandidate(iceCandidate, localParticipant?.connectionId)
                }

                override fun onSignalingChange(signalingState: SignalingState) {
                    if (SignalingState.STABLE == signalingState) {
                        // SDP Offer/Answer finished. Add stored remote candidates.
                        val it: MutableIterator<IceCandidate> =
                            localParticipant?.iceCandidateList!!.iterator()
                        while (it.hasNext()) {
                            val candidate = it.next()
                            localParticipant!!.peerConnection?.addIceCandidate(candidate)
                            it.remove()
                        }
                    }
                }
            })
        if (localParticipant?.audioTrack != null) {
            if (peerConnection != null) {
                peerConnection.addTransceiver(
                    localParticipant!!.audioTrack,
                    RtpTransceiverInit(RtpTransceiver.RtpTransceiverDirection.SEND_ONLY)
                )
            }
        }
        if (localParticipant?.videoTrack != null) {
            if (peerConnection != null) {
                peerConnection.addTransceiver(
                    localParticipant!!.videoTrack,
                    RtpTransceiverInit(RtpTransceiver.RtpTransceiverDirection.SEND_ONLY)
                )
            }
        }
        return peerConnection
    }

    fun createRemotePeerConnection(connectionId: String) {
        val config = RTCConfiguration(if (iceServers.isEmpty()) iceServersDefault else iceServers)
        config.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.ENABLED
        config.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE
        config.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.NEGOTIATE
        config.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY
        config.keyType = PeerConnection.KeyType.ECDSA
        config.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN
        val peerConnection: PeerConnection? = peerConnectionFactory?.createPeerConnection(
            config,
            object : CustomPeerConnectionObserver("remotePeerCreation") {
                override fun onIceCandidate(iceCandidate: IceCandidate) {
                    iceCandidate?.let { super.onIceCandidate(it) }
                    if (iceCandidate != null) {
                        websocket?.onIceCandidate(iceCandidate, connectionId)
                    }
                }

                override fun onAddTrack(rtpReceiver: RtpReceiver, mediaStreams: Array<MediaStream>) {
                    super.onAddTrack(rtpReceiver, mediaStreams)
                    remoteParticipants[connectionId]?.let {
                        activity.setRemoteMediaStream(mediaStreams[0],
                            it
                        )
                    }
                }

                override fun onSignalingChange(signalingState: SignalingState) {
                    if (SignalingState.STABLE == signalingState) {
                        // SDP Offer/Answer finished. Add stored remote candidates.
                        val remoteParticipant: RemoteParticipant? = remoteParticipants[connectionId]
                        val it: MutableIterator<IceCandidate> =
                            remoteParticipant?.iceCandidateList?.iterator() as MutableIterator<IceCandidate>
                        while (it.hasNext()) {
                            val candidate = it.next()
                            remoteParticipant?.peerConnection!!.addIceCandidate(candidate)
                            it.remove()
                        }
                    }
                }
            })
        if (peerConnection != null) {
            peerConnection.addTransceiver(
                MediaStreamTrack.MediaType.MEDIA_TYPE_AUDIO,
                RtpTransceiverInit(RtpTransceiver.RtpTransceiverDirection.RECV_ONLY)
            )
        }
        if (peerConnection != null) {
            peerConnection.addTransceiver(
                MediaStreamTrack.MediaType.MEDIA_TYPE_VIDEO,
                RtpTransceiverInit(RtpTransceiver.RtpTransceiverDirection.RECV_ONLY)
            )
        }
        remoteParticipants[connectionId]?.peerConnection = peerConnection

    }

    fun createOfferForPublishing(constraints: MediaConstraints?) {
        localParticipant?.peerConnection?.createOffer(object : CustomSdpObserver("createOffer") {
            override fun onCreateSuccess(sdp: SessionDescription) {
                super.onCreateSuccess(sdp)
                Log.i("createOffer SUCCESS", sdp.toString())
                localParticipant!!.peerConnection?.setLocalDescription(object :
                    CustomSdpObserver("createOffer_setLocalDescription") {
                    override fun onSetSuccess() {
                        super.onSetSuccess()
                        websocket?.publishVideo(sdp)
                    }
                }, sdp)
            }
        }, constraints)
    }

    fun createAnswerForSubscribing(
        remoteParticipant: RemoteParticipant,
        streamId: String?,
        constraints: MediaConstraints?
    ) {
        remoteParticipant.peerConnection
            ?.createAnswer(object : CustomSdpObserver("createAnswerSubscribing") {
                override fun onCreateSuccess(sdp: SessionDescription) {
                    super.onCreateSuccess(sdp)
                    Log.i("createAnswer SUCCESS", sdp.toString())
                    remoteParticipant.peerConnection!!.setLocalDescription(object :
                        CustomSdpObserver("createAnswerSubscribing_setLocalDescription") {
                        override fun onSetSuccess() {
                            super.onSetSuccess()
                            if (streamId != null) {
                                websocket?.receiveVideoFrom(sdp, remoteParticipant, streamId)
                            }
                        }
                    }, sdp)
                }
            }, constraints)
    }

    fun setIceServers(iceServers: List<IceServer?>) {
        this.iceServers = iceServers
    }

    fun getRemoteParticipant(id: String): RemoteParticipant? {
        return remoteParticipants[id]
    }

    fun addRemoteParticipant(remoteParticipant: RemoteParticipant) {
        remoteParticipants[remoteParticipant.connectionId.toString()] = remoteParticipant
    }

    fun removeRemoteParticipant(id: String): RemoteParticipant? {
        return remoteParticipants.remove(id)
    }

    fun leaveSession() {
        AsyncTask.execute {
            websocket?.setWebsocketCancelled(true)

                websocket!!.leaveRoom()
                websocket!!.disconnect()

            localParticipant!!.dispose()
        }
        activity.runOnUiThread {
            for (remoteParticipant in remoteParticipants.values) {
                if (remoteParticipant.peerConnection != null) {
                    remoteParticipant.peerConnection!!.close()
                }
                views_container.removeView(remoteParticipant.view)
            }
        }
        AsyncTask.execute {
                peerConnectionFactory!!.dispose()
                peerConnectionFactory = null
        }
    }

    fun removeView(view: View?) {
        views_container.removeView(view)
    }
}
