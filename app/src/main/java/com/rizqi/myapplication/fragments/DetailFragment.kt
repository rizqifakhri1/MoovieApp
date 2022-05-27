
package com.rizqi.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rizqi.myapplication.R
import com.rizqi.myapplication.databinding.FragmentDetailBinding
import com.rizqi.myapplication.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()
    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"

    private val args : DetailFragmentArgs by navArgs()

/*    private lateinit var detailViewModel: DetailViewModel*/

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
        /*detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)*/
        val id = args.id
        detailViewModel.getMovieById(id)
        detailViewModel.detailMovie.observe(viewLifecycleOwner) {
            Glide.with(binding.root).load(IMAGE_BASE+it.data?.posterPath).into(binding.ivPoster)
            binding.tvJudul.text = it.data?.originalTitle
            binding.tvTextOverview.text = it.data?.overview
            binding.tvBahasa.text = it.data?.originalLanguage
            binding.tvRelease.text = it.data?.releaseDate
            binding.tvScore.text = it.data?.voteAverage.toString()
            binding.tvPopular.text = it.data?.popularity.toString()
        }

        binding.ivBackbutton.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeScreenFragment)
        }

        binding.ivBtnPesanTiket.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_pesanFragment)
        }
    }
}