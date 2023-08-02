package com.ssafy.phonesin.ui.module.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.ssafy.phonesin.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FrameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        val imagePath = intent.getStringExtra("imagePath")
        val cameraFacing = intent.getStringExtra("cameraFacing")
        val imageView = findViewById<ImageView>(R.id.imageView)
        val saveButton = findViewById<TextView>(R.id.textViewTitle)

        val originalBitmap = BitmapFactory.decodeFile(imagePath)
        Log.d("FrameActivity", "Original Bitmap: $originalBitmap")

        val rotationDegrees = if (cameraFacing == "FRONT") 270f else 90f
        val matrix = Matrix().apply { postRotate(rotationDegrees) }

        val rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, true)

        imageView.setImageBitmap(rotatedBitmap)

        saveButton.setOnClickListener {
            val viewFrame = findViewById<View>(R.id.rootView)
            val frameBitmap = layoutToBitmap(viewFrame)

            frameBitmap?.let {
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val imageFile = File(storageDir, "IMG_$timeStamp.jpg")

                try {
                    val fos = FileOutputStream(imageFile)
                    frameBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()

                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageFile.toUri()))
                    Log.d("tag", "사진 저장 ${imageFile.toUri()}")
                } catch (e: Exception) {
                    Log.d("tag", "사진 저장 실패 ${imageFile.toUri()}")
                } finally {
                    frameBitmap.recycle()



                }
            }
        }
    }

    private fun layoutToBitmap(layout: View): Bitmap? {
        layout.isDrawingCacheEnabled = true
        layout.buildDrawingCache()
        val drawingCache = layout.drawingCache
        if (drawingCache != null) {
            val bitmap = Bitmap.createBitmap(drawingCache)
            layout.isDrawingCacheEnabled = false
            return bitmap
        }
        layout.isDrawingCacheEnabled = false
        return null
    }
}
