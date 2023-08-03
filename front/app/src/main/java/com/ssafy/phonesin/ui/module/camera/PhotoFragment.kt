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
        showImage(photoPath)
    }

    private fun showImage(photoPath: String) {
        val bitmap = BitmapFactory.decodeFile(photoPath)

        val rotationDegrees = 90f
        val matrix = Matrix().apply { postRotate(rotationDegrees) }

        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        binding.photoViewer.setImageBitmap(rotatedBitmap)
    }

    companion object {
        private const val ARG_PHOTO_PATH = "photo_path"

        fun newInstance(photoPath: String): PhotoFragment {
            val fragment = PhotoFragment()
            val args = Bundle().apply {
                putString(ARG_PHOTO_PATH, photoPath)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
