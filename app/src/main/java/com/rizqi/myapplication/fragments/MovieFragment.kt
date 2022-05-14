package com.rizqi.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rizqi.myapplication.R
import com.rizqi.myapplication.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivHome.setOnClickListener{
            findNavController().navigate(R.id.action_movieFragment_to_homeScreenFragment)
        }
        binding.ivProfil.setOnClickListener {
            findNavController().navigate(R.id.action_movieFragment_to_profileFragment)
        }
    }
}