package com.rizqi.myapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.rizqi.myapplication.R
import com.rizqi.myapplication.adapter.PopularAdapter
import com.rizqi.myapplication.adapter.UpcomingAdapter
import com.rizqi.myapplication.databinding.FragmentHomeScreenBinding
import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.GetAllMovieUpcoming
import com.rizqi.myapplication.model.ResultAdapter
import com.rizqi.myapplication.model.ResultUpcoming
import com.rizqi.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenFragment : Fragment() {

    private var _binding : FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

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

        //Implementasi Save Preferences
        preferences = requireContext().getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)
        //Untuk menyapa user Assalamualaikum ${username}
        binding.tvMenyapaNama.text = "${preferences.getString(LoginFragment.USERNAME,null)}"

        binding.tvDesc.setOnClickListener{
            findNavController().navigate(R.id.action_homeScreenFragment_to_detailFragment)
        }

        binding.ivProfil.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_profileFragment2)
        }
        binding.ivMiddle.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_movieFragment)
        }

        fetchAllMoviePopular()
        fetchAllMovieUpcoming()
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
                val toDetail = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailFragment(id)
                findNavController().navigate(toDetail)
            }
        })
        adapter.submitData(data)
        binding.rvHighlight.adapter = adapter
    }


    private fun fetchAllMovieUpcoming(){
        ApiClient.instance.getAllMovieUpcoming()
            .enqueue(object : Callback<GetAllMovieUpcoming> {

                override fun onResponse(
                    call: Call<GetAllMovieUpcoming>,
                    response: Response<GetAllMovieUpcoming>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        body?.let { showListMovieUpcoming(it.results)
                        }

                    }

                }

                override fun onFailure(call: Call<GetAllMovieUpcoming>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showListMovieUpcoming(data: List<ResultUpcoming>) {
        val adapter = UpcomingAdapter(object : UpcomingAdapter.OnClickListener{
            override fun onClickItem(data: ResultUpcoming) {
                val id = data.id
                val toDetail = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailFragment(id)
                findNavController().navigate(toDetail)

            }
        })
        adapter.submitData(data)
        binding.rvPopuler.adapter = adapter
    }

}