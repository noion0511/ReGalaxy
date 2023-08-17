package com.ssafy.phonesin.common.openvidu

import android.util.Log
import org.webrtc.AudioTrack
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.VideoTrack

abstract class Participant {
    var connectionId: String? = null
    var participantName: String
        protected set
    protected var session: Session
    var iceCandidateList: MutableList<IceCandidate> = mutableListOf()
        protected set
    var peerConnection: PeerConnection? = null
    var audioTrack: AudioTrack? = null
    var videoTrack: VideoTrack? = null
    var mediaStream: MediaStream? = null

    constructor(participantName: String, session: Session) {
        this.participantName = participantName
        this.session = session
    }

    constructor(connectionId: String?, participantName: String, session: Session) {
        this.connectionId = connectionId
        this.participantName = participantName
        this.session = session
    }

    open fun dispose() {
        if (peerConnection != null) {
            try {
                peerConnection!!.close()
            } catch (e: IllegalStateException) {
                Log.e("Dispose PeerConnection", e.message!!)
            }
        }
    }
}
