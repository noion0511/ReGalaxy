package com.ssafy.phonesin.common.openvidu

import android.view.View
import android.widget.TextView
import org.webrtc.SurfaceViewRenderer


class RemoteParticipant(connectionId: String?, participantName: String?, session: Session?) :
    Participant(connectionId, participantName!!, session!!) {
    var view: View? = null
    var videoView: SurfaceViewRenderer? = null
    var participantNameText: TextView? = null

    init {
        this.session.addRemoteParticipant(this)
    }

    override fun dispose() {
        super.dispose()
    }
}
