package com.ssafy.phonesin.ui.module.camera

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
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
        val imageView = findViewById<ImageView>(R.id.imageView)

        val originalBitmap = BitmapFactory.decodeFile(imagePath)
        val matrix = Matrix().apply { postRotate(90f) }
        val rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, true)

        val viewFrame = findViewById<View>(R.id.viewFrame)
        viewFrame.post {
            val frameBitmap = layoutToBitmap(viewFrame)

            if (frameBitmap != null) {
                val borderWidth = 10
                val borderColor = Color.BLACK
                val rotatedBitmapWithBorder = addBorderToBitmap(rotatedBitmap, borderWidth, borderColor)
                imageView.setImageBitmap(rotatedBitmapWithBorder)

                val combinedBitmap = combineBitmaps(frameBitmap, rotatedBitmapWithBorder)

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val imageFile = File(storageDir, "IMG_$timeStamp.jpg")

                try {
                    val fos = FileOutputStream(imageFile)
                    combinedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()

                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageFile.toUri()))
                    Log.d("tag", "사진 저장 ${imageFile.toUri()}")
                } catch (e: Exception) {
                    Log.d("tag", "사진 저장 실패 ${imageFile.toUri()}")
                } finally {
                }
            }
        }
    }


    private fun addBorderToBitmap(bitmap: Bitmap, borderWidth: Int, color: Int): Bitmap {
        val borderBitmap = Bitmap.createBitmap(bitmap.width + borderWidth * 2, bitmap.height + borderWidth * 2, bitmap.config)
        val canvas = Canvas(borderBitmap)

        val paint = Paint()
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth.toFloat()

        canvas.drawBitmap(bitmap, borderWidth.toFloat(), borderWidth.toFloat(), null)
        canvas.drawRect(borderWidth.toFloat(), borderWidth.toFloat(), bitmap.width + borderWidth.toFloat(), bitmap.height + borderWidth.toFloat(), paint)

        return borderBitmap
    }


    private fun combineBitmaps(background: Bitmap, overlay: Bitmap): Bitmap {
        val scaledOverlay = Bitmap.createScaledBitmap(overlay, background.width, background.height, false)
        val combined = Bitmap.createBitmap(background.width, background.height, background.config)
        val canvas = Canvas(combined)
        canvas.drawBitmap(background, Matrix(), null)
        canvas.drawBitmap(scaledOverlay, Matrix(), null)
        return combined
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