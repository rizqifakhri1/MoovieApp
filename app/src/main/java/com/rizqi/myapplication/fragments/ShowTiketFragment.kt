package com.rizqi.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rizqi.myapplication.R
import com.rizqi.myapplication.databinding.FragmentShowTiketBinding

class ShowTiketFragment : Fragment() {

    private var _binding : FragmentShowTiketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowTiketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivTiket.setOnClickListener {
            findNavController().navigate(R.id.action_showTiketFragment_to_qrcodeFragment)
        }
    }

}