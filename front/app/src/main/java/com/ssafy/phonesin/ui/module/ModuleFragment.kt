package com.ssafy.phonesin.ui.module

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentModuleBinding
import com.ssafy.phonesin.model.ModuleType
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModuleFragment : BaseFragment<FragmentModuleBinding>(
    R.layout.fragment_module
) {

    private val PERMISSIONS_REQUEST_CODE = 100
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentModuleBinding {
        return FragmentModuleBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    REQUIRED_PERMISSIONS[0]
                )
            ) {
                // 사용자가 임시로 권한을 거부한 경우
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                // 처음 권한을 요청하거나 사용자가 '다시 묻지 않음'을 선택한 경우
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        bindingNonNull.recyclerViewModule.layoutManager = layoutManager

        val items = listOf(
            ModuleType(
                backgroundColor = resources.getColor(R.color.moduleBackgroundGreen),
                imageResource = R.drawable.camera,
                title = getString(R.string.module_type_camera_title),
                content = resources.getString(R.string.module_type_camera_content)
            ),
            ModuleType(
                backgroundColor = resources.getColor(R.color.moduleBackgroundYellow),
                imageResource = R.drawable.remote_tv,
                title = getString(R.string.module_type_remote_title),
                content = resources.getString(R.string.module_type_remote_content)
            ),
            ModuleType(
                backgroundColor = resources.getColor(R.color.moduleBackgroundBlue),
                imageResource = R.drawable.cctv,
                title = getString(R.string.module_type_cctv_title),
                content = resources.getString(R.string.module_type_cctv_content)
            ),
            ModuleType(
                backgroundColor = resources.getColor(R.color.moduleBackgroundRed),
                imageResource = R.drawable.temperature,
                title = getString(R.string.module_type_temperature_title),
                content = resources.getString(R.string.module_type_temperature_content)
            ),
        )

        val adapter = ModuleAdapter(items, object : ModuleAdapter.OnItemClickListener {
            override fun onItemClick(item: ModuleType) {
                when (item.title) {
                    getString(R.string.module_type_camera_title) -> {
                        findNavController().navigate(R.id.action_module_to_cameraXFragment)
                    }
                    getString(R.string.module_type_cctv_title) -> showToast("준비중입니다.")
                    getString(R.string.module_type_temperature_title) -> showToast("준비중입니다.")
                    getString(R.string.module_type_remote_title) -> findNavController().navigate(R.id.action_module_to_irRemoteFragment)
                }
            }
        })

        bindingNonNull.recyclerViewModule.adapter = adapter

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finish()
        }
    }
}