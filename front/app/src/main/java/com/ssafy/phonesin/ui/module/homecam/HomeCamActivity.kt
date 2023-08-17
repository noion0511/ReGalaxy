package com.ssafy.phonesin.ui.module.homecam

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.openvidu.LocalParticipant
import com.ssafy.phonesin.common.openvidu.RemoteParticipant
import com.ssafy.phonesin.common.openvidu.Session
import com.ssafy.phonesin.common.utils.CustomHttpClient
import com.ssafy.phonesin.common.websocket.CustomWebSocket
import com.ssafy.phonesin.databinding.ActivityHomeCamBinding
import com.ssafy.phonesin.ui.signup.LoadingDialog
import com.ssafy.phonesin.ui.util.setDebouncingClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    var homeCamName = ""

    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeCamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeCamName = intent.getStringExtra("homeCamName").toString()
        Log.e("싸피", homeCamName)

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
        binding.textViewPlus.toolbarBackButtonTitle.text = getString(R.string.module_home_cam)
        binding.textViewPlus.toolbarBackButton.setDebouncingClickListener {
            finish()
        }
    }

    private fun getToken(sessionId: String) {
        val dialog = LoadingDialog(this)
        dialog.show()
        val handler = Handler()
        handler.postDelayed({
            dialog.dismiss()
        }, 3500)
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

                                    getTokenSuccess(responseString.toString(), sessionId)

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
//            val toast: Toast = Toast.makeText(this, "Error connecting to $url", Toast.LENGTH_LONG)
//            toast.show()
            binding.localGlSurfaceView.clearImage()
            binding.localGlSurfaceView.release()
            binding.mainParticipant.setText(null)
            binding.mainParticipant.setPadding(0, 0, 0, 0)
        }
        Handler(this.getMainLooper()).post(myRunnable)
    }

    private fun getTokenSuccess(token: String, sessionId: String) {
        // Initialize our session
        session = Session(sessionId, token, binding.viewsContainer, this)

        // Initialize our local participant and start local camera

        val localParticipant =
            LocalParticipant(homeCamName, session!!, this, binding.localGlSurfaceView)
        localParticipant.startCamera()
        CoroutineScope(Dispatchers.Main).launch {
            binding.mainParticipant.text = homeCamName
//            binding.mainParticipant.setPadding(20, 3, 20, 3)
            Log.e("싸피", homeCamName.toString())
        }
        runOnUiThread {
            binding.mainParticipant.setText(homeCamName)
//            binding.mainParticipant.setPadding(20, 3, 20, 3)
            Log.e("싸피", " 쓰")
        }
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
        binding.mainParticipant.setText(null)
        binding.mainParticipant.setPadding(0, 0, 0, 0)
    }

    override fun onDestroy() {
        leaveSession()
        super.onDestroy()
    }

    override fun onBackPressed() {
        // leaveSession()
        super.onBackPressed()
    }

    override fun onStop() {
        // leaveSession()
        super.onStop()
    }

}