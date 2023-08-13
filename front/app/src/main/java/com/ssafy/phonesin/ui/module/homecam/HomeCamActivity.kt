package com.ssafy.phonesin.ui.module.homecam

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.ssafy.phonesin.common.openvidu.LocalParticipant
import com.ssafy.phonesin.common.openvidu.RemoteParticipant
import com.ssafy.phonesin.common.openvidu.Session
import com.ssafy.phonesin.common.utils.CustomHttpClient
import com.ssafy.phonesin.common.websocket.CustomWebSocket
import com.ssafy.phonesin.databinding.ActivityHomeCamBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import org.webrtc.EglBase
import org.webrtc.MediaStream
import java.io.IOException
import java.util.Random


private const val TAG = "CamListFragment"

class HomeCamActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeCamBinding
    private var httpClient: CustomHttpClient? = null

    val APPLICATION_SERVER_URL = "https://demos.openvidu.io/"

    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeCamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        ButterKnife.bind(this)
        val random = Random()
        val randomIndex = random.nextInt(100)

        initViews()

        httpClient = CustomHttpClient("https://demos.openvidu.io/")
        getToken("temptemp")

        setHomeCam()

    }

    private fun setHomeCam() {

    }

    private fun getToken(sessionId: String) {
        try {
            // Session Request
            val sessionBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                "{\"customSessionId\": \"$sessionId\"}"
            )
            httpClient!!.httpCall(
                "/api/sessions",
                "POST",
                "application/json",
                sessionBody,
                object : Callback {
                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        Log.d(TAG, "responseString: " + response.body()!!.string())

                        // Token Request
                        val tokenBody = RequestBody.create(
                            MediaType.parse("application/json; charset=utf-8"),
                            "{}"
                        )
                        httpClient!!.httpCall(
                            "/api/sessions/$sessionId/connections",
                            "POST",
                            "application/json",
                            tokenBody,
                            object : Callback {
                                override fun onResponse(call: Call, response: Response) {
                                    var responseString: String? = null
                                    try {
                                        responseString = response.body()!!.string()
                                    } catch (e: IOException) {
                                        Log.e(TAG, "Error getting body", e)
                                    }
                                    if (responseString != null) {
                                        getTokenSuccess(responseString, sessionId)
                                    }
                                }

                                override fun onFailure(call: Call, e: IOException) {
                                    Log.e(TAG, "Error POST /api/sessions/SESSION_ID/connections", e)
                                    connectionError(APPLICATION_SERVER_URL)
                                }
                            })
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        Log.e(TAG, "Error POST /api/sessions", e)
                        connectionError(APPLICATION_SERVER_URL)
                    }
                })
        } catch (e: IOException) {
            Log.e(TAG, "Error getting token", e)
            e.printStackTrace()
            connectionError(APPLICATION_SERVER_URL)
        }
    }

    private fun connectionError(url: String) {
        val myRunnable = Runnable {
            val toast: Toast = Toast.makeText(this, "Error connecting to $url", Toast.LENGTH_LONG)
            toast.show()
            binding.localGlSurfaceView.clearImage()
            binding.localGlSurfaceView.release()
        }
        Handler(this.getMainLooper()).post(myRunnable)
    }

    private fun getTokenSuccess(token: String, sessionId: String) {
        // Initialize our session
        session = Session(sessionId, token, binding.viewsContainer, this)

        // Initialize our local participant and start local camera

        val localParticipant =
            LocalParticipant("", session!!, this, binding.localGlSurfaceView)
        localParticipant.startCamera()


        // Initialize and connect the websocket to OpenVidu Server
        startWebSocket()
    }

    private fun startWebSocket() {
        val webSocket = session?.let { CustomWebSocket(it, this) }
        if (webSocket != null) {
            webSocket.execute()
        }
        session!!.setWebSocket(webSocket)
    }

    private fun initViews() {
        val rootEglBase = EglBase.create()
        binding.localGlSurfaceView.apply {
            init(rootEglBase.eglBaseContext, null)
            setMirror(true)
            setEnableHardwareScaler(true)
            setZOrderMediaOverlay(true)
        }

    }

//    fun createRemoteParticipantVideo(
//        remoteParticipant: RemoteParticipant,
//        videoViewRes: String,
//        containerViewRes: String
//    ) {
//        val mainHandler = Handler(this.mainLooper)
//        val myRunnable = Runnable {
//            val videoViewId = baseContext.resources.getIdentifier(
//                videoViewRes,
//                "id", baseContext.packageName
//            )
//            val videoView = findViewById<SurfaceViewRenderer>(videoViewId)
//            remoteParticipant.videoView = (videoView)
//            videoView.setMirror(false)
//            val rootEglBase = EglBase.create()
//            videoView.init(rootEglBase.eglBaseContext, null)
//            videoView.setZOrderMediaOverlay(true)
//            val containerViewId =
//                baseContext.resources.getIdentifier(containerViewRes, "id", baseContext.packageName)
//            val containerView = findViewById<FrameLayout>(containerViewId)
//            remoteParticipant.view = (containerView)
//        }
//        mainHandler.post(myRunnable)
//    }

    fun setRemoteMediaStream(stream: MediaStream, remoteParticipant: RemoteParticipant) {
        val videoTrack = stream.videoTracks[0]
        videoTrack.addSink(remoteParticipant.videoView)
        runOnUiThread { remoteParticipant.videoView!!.visibility = View.VISIBLE }
    }


    fun leaveSession() {
        if (session != null) {
            session!!.leaveSession()
        }
        if (httpClient != null) {
            httpClient!!.dispose()
        }
        binding.localGlSurfaceView.clearImage()
        binding.localGlSurfaceView.release()
    }

    override fun onDestroy() {
        leaveSession()
        super.onDestroy()
    }

    override fun onBackPressed() {
        leaveSession()
        super.onBackPressed()
    }

    override fun onStop() {
        leaveSession()
        super.onStop()
    }

}