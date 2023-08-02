package com.ssafy.phonesin.ui.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentModuleBinding
import com.ssafy.phonesin.model.ModuleType

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ModuleFragment : Fragment() {
    private lateinit var binding: FragmentModuleBinding
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewModule.layoutManager = layoutManager

        val items = listOf(
            ModuleType(
                backgroundColor = resources.getColor(R.color.moduleBackgroundGreen),
                imageResource = R.drawable.camera,
                title = getString(R.string.module_type_camera_title),
                content = resources.getString(R.string.module_type_camera_content)
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
                when(item.title) {
                    getString(R.string.module_type_camera_title) ->  findNavController().navigate(R.id.action_module_to_cameraFragment)
                    getString(R.string.module_type_cctv_title) -> findNavController().navigate(R.id.action_module_to_camListFragment)
                    getString(R.string.module_type_temperature_title) -> findNavController().navigate(R.id.action_module_to_hygrometerFragment)
                }
            }
        })

        binding.recyclerViewModule.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}