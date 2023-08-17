package com.ssafy.phonesin.common.utils

import android.os.Build
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.openvidu.RemoteParticipant
import org.webrtc.EglBase
import org.webrtc.MediaStream
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoTrack

fun AppCompatActivity.setRemoteMediaStream(
    stream: MediaStream,
    remoteParticipant: RemoteParticipant
) {

    val videoTrack: VideoTrack = stream.videoTracks.first()
    videoTrack.addSink(remoteParticipant.videoView)

    runOnUiThread {
        remoteParticipant.videoView?.visibility = View.VISIBLE
    }
}


fun AppCompatActivity.createRemoteParticipantVideo(
    remoteParticipant: RemoteParticipant,
    videoViewRes: String,
    containerViewRes: String
) {
    val mainHandler = Handler(this.mainLooper)
    val myRunnable = Runnable {
//        val videoViewId = baseContext.resources.getIdentifier(
//            videoViewRes,
//            "id", baseContext.packageName
//        )
//        val videoView = findViewById<SurfaceViewRenderer>(videoViewId)
//        remoteParticipant.videoView = (videoView)
//        videoView.setMirror(false)
//        val rootEglBase = EglBase.create()
//        videoView.init(rootEglBase.eglBaseContext, null)
//        videoView.setZOrderMediaOverlay(true)
//        val containerViewId =
//            baseContext.resources.getIdentifier(containerViewRes, "id", baseContext.packageName)
//        val containerView = findViewById<FrameLayout>(containerViewId)
//        remoteParticipant.view = (containerView)
        val rowView: View = this.layoutInflater.inflate(R.layout.peer_video, null)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 0, 0, 20)
        rowView.layoutParams = lp
        val rowId = View.generateViewId()
        rowView.id = rowId

        val containerViewId =
            baseContext.resources.getIdentifier(containerViewRes, "id", baseContext.packageName)
        val containerView = findViewById<LinearLayout>(containerViewId)
        containerView.addView(rowView)

//
//        remoteParticipant.view = (containerView)


        val videoView = (rowView as ViewGroup).getChildAt(0) as SurfaceViewRenderer
        remoteParticipant.videoView = (videoView)
        videoView.setMirror(false)
        val rootEglBase = EglBase.create()
        videoView.init(rootEglBase.eglBaseContext, null)
        videoView.setZOrderMediaOverlay(true)

        val textView = rowView.getChildAt(1)
        remoteParticipant.participantNameText = (textView as TextView)
        remoteParticipant.view = (rowView)

        remoteParticipant.participantNameText!!.text = (remoteParticipant.participantName)
        remoteParticipant.participantNameText!!.setPadding(20, 3, 20, 3)

    }
    mainHandler.post(myRunnable)
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun AppCompatActivity.createRemoteScreenVideo(
    remoteParticipant: RemoteParticipant,
    containerRes: String,
    pearViewRes: String,
    num: Int
) {
    val mainHandler = Handler(this.mainLooper)
    val myRunnable = Runnable {
        val containerViewId = baseContext.resources.getIdentifier(
            containerRes,
            "id", baseContext.packageName
        )
        val containerView = findViewById<RelativeLayout>(containerViewId)
        val pearViewId = baseContext.resources.getIdentifier(
            pearViewRes,
            "layout", baseContext.packageName
        )
        val rowView = this.layoutInflater.inflate(pearViewId, null)
        val rp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        rowView.layoutParams = rp
        val rowId = View.generateViewId()
        rowView.id = rowId
        rowView.translationZ = num.toFloat()
        containerView.addView(rowView)
        containerView.bringChildToFront(rowView)
        val videoView = (rowView as ViewGroup).getChildAt(0) as SurfaceViewRenderer
        remoteParticipant.videoView = (videoView)
        videoView.setMirror(false)
        val rootEglBase = EglBase.create()
        videoView.init(rootEglBase.eglBaseContext, null)
        //videoView.setZOrderMediaOverlay(true)
        remoteParticipant.view = (rowView)
    }
    mainHandler.post(myRunnable)
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun AppCompatActivity.resizeView(
    toggle: Boolean,
    containerViewRes: String
) {
    val mainHandler = Handler(this.mainLooper)
    val myRunnable = Runnable {
        val containerViewId = baseContext.resources.getIdentifier(
            containerViewRes,
            "id", baseContext.packageName
        )
        val containerView = findViewById<FrameLayout>(containerViewId)
        var width: Int
        var height: Int

        if (toggle) {
            width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                90f, resources.displayMetrics
            ).toInt()
            height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                120f, resources.displayMetrics
            ).toInt()

        } else {
            width = RelativeLayout.LayoutParams.MATCH_PARENT
            height = RelativeLayout.LayoutParams.MATCH_PARENT
        }
        containerView.layoutParams = RelativeLayout.LayoutParams(width, height)
        containerView.translationZ = 90f
    }
    mainHandler.post(myRunnable)
}