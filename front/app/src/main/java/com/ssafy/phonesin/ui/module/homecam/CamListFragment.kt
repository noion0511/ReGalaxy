package com.ssafy.phonesin.ui.module.homecam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentCamListBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG = "CamListFragment"

class CamListFragment : Fragment() {
    private lateinit var binding: FragmentCamListBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCamListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        binding.floatingActionButton.setOnClickListener {
            if (binding.editTextHomeCamName.text.toString() != "") {
                val intent = Intent(requireActivity(), HomeCamActivity::class.java)
                intent.putExtra("homeCamName", binding.editTextHomeCamName.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "이름을 적어주세요", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun initToolbar() {
        binding.textViewPlus.toolbarBackButtonTitle.text = getString(R.string.module_home_cam)
        binding.textViewPlus.toolbarBackButton.setDebouncingClickListener {
            findNavController().navigate(R.id.action_camListFragment_to_module)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CamListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}