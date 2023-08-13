package com.ssafy.phonesin.ui.module

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentModuleBinding
import com.ssafy.phonesin.model.ModuleType
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModuleFragment : BaseFragment<FragmentModuleBinding>(
    R.layout.fragment_module
) {
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentModuleBinding {
        return FragmentModuleBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            findNavController().navigate(R.id.action_module_to_cameraXFragment)
                        } else {
                            findNavController().navigate(R.id.action_module_to_cameraFragment)
                        }
                    }
                    getString(R.string.module_type_cctv_title) -> findNavController().navigate(R.id.action_module_to_camListFragment)
                    getString(R.string.module_type_temperature_title) -> findNavController().navigate(
                        R.id.action_module_to_hygrometerFragment
                    )
                    getString(R.string.module_type_remote_title) -> findNavController().navigate(R.id.action_module_to_irRemoteFragment)
                }
            }
        })

        bindingNonNull.recyclerViewModule.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(false)
    }
}