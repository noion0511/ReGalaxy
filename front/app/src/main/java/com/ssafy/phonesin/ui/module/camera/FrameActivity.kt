package com.ssafy.phonesin.ui.module.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
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
import java.util.Date
import java.util.Locale

class FrameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        val imagePath = intent.getStringExtra("imagePath")
        val imageView = findViewById<ImageView>(R.id.imageView)


        val originalBitmap = BitmapFactory.decodeFile(imagePath)
        val matrix = Matrix().apply {
            postRotate(90f)
        }

        val rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, true)
        imageView.setImageBitmap(rotatedBitmap)

        val rootView = findViewById<View>(R.id.rootView)
        rootView.post {
            val frameBitmap = createBitmapFromView(rootView)
            val combinedBitmap = combineBitmaps(rotatedBitmap, frameBitmap)

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
                originalBitmap.recycle()
                rotatedBitmap.recycle()
                frameBitmap.recycle()
                combinedBitmap.recycle()
            }
        }
    }

    private fun createBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null)
            bgDrawable.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmap
    }

    private fun combineBitmaps(background: Bitmap, overlay: Bitmap): Bitmap {
        val combined = Bitmap.createBitmap(background.width, background.height, background.config)
        val canvas = Canvas(combined)
        canvas.drawBitmap(background, Matrix(), null)
        canvas.drawBitmap(overlay, Matrix(), null)
        return combined
    }
}