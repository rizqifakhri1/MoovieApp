
package com.rizqi.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rizqi.myapplication.R
import com.rizqi.myapplication.databinding.FragmentDetailBinding
import com.rizqi.myapplication.databinding.FragmentHomeScreenBinding
import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.GetDetail
import com.rizqi.myapplication.network.ApiClient
import com.rizqi.myapplication.viewmodel.DetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"

    private val args : DetailFragmentArgs by navArgs()

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        val id = args.id
        detailViewModel.getDetail(id)
        detailViewModel.dataDetail.observe(viewLifecycleOwner){
            Glide.with(binding.root).load(IMAGE_BASE+it.posterPath).into(binding.ivPoster)
            binding.tvJudul.text = it.originalTitle
        }

    }
}