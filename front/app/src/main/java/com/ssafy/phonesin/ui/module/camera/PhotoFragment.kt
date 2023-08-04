package com.ssafy.phonesin.ui.module.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.phonesin.databinding.FragmentPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : Fragment() {
    private lateinit var binding: FragmentPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoPath = arguments?.getString(ARG_PHOTO_PATH) ?: return
        val cameraFacing = arguments?.getString(ARG_CAMERA_FACE) ?: return
        showImage(photoPath, cameraFacing)
    }

    private fun showImage(photoPath: String, cameraFacing: String) {
        val bitmap = BitmapFactory.decodeFile(photoPath)

        val rotationDegrees = if (cameraFacing == "FRONT") 270f else 90f
        val matrix = Matrix().apply { postRotate(rotationDegrees) }

        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        binding.photoViewer.setImageBitmap(rotatedBitmap)
    }

    companion object {
        private const val ARG_PHOTO_PATH = "photo_path"
        private const val ARG_CAMERA_FACE = "camera_face"

        fun newInstance(photoPath: String, cameraFacing: String): PhotoFragment {
            val fragment = PhotoFragment()
            val args = Bundle().apply {
                putString(ARG_PHOTO_PATH, photoPath)
                putString(ARG_CAMERA_FACE, cameraFacing)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
