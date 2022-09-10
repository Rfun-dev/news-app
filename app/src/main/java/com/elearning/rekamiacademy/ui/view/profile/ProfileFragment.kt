package com.elearning.rekamiacademy.ui.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elearning.rekamiacademy.databinding.FragmentProfileBinding
import com.elearning.rekamiacademy.util.NavHelper.safeNavigate

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val direction = ProfileFragmentDirections.actionProfileFragmentToListNewsFragment()
        safeNavigate(direction,javaClass.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (_binding?.root?.parent != null) (_binding!!.root.parent as ViewGroup).removeView(
            _binding!!.root)
    }
}