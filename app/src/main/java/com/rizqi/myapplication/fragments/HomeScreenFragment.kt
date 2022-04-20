package com.rizqi.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.rizqi.myapplication.R
import com.rizqi.myapplication.adapter.PopularAdapter
import com.rizqi.myapplication.databinding.FragmentHomeScreenBinding
import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.ResultAdapter
import com.rizqi.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenFragment : Fragment() {

    private var _binding : FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDesc.setOnClickListener{
            findNavController().navigate(R.id.action_homeScreenFragment_to_detailFragment)
        }
        fetchAllMoviePopular()
    }

    private fun fetchAllMoviePopular(){
        ApiClient.instance.getAllMoviePopuler()
            .enqueue(object : Callback<GetAllMoviePopular> {

                override fun onResponse(
                    call: Call<GetAllMoviePopular>,
                    response: Response<GetAllMoviePopular>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        body?.let { showListMoviePopular(it.results)
                        }

                    }

                }

                override fun onFailure(call: Call<GetAllMoviePopular>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showListMoviePopular(data: List<ResultAdapter>) {
        val adapter = PopularAdapter(object : PopularAdapter.OnClickListener{
            override fun onClickItem(data: ResultAdapter) {
                val id = data.id
            }
        })
        adapter.submitData(data)
        binding.rvHighlight.adapter = adapter
    }

}