package com.rizqi.myapplication.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rizqi.myapplication.R
import com.rizqi.myapplication.databinding.FragmentProfileBinding
import com.rizqi.myapplication.fragments.LoginFragment.Companion.LOGINUSER


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LOGINUSER, Context.MODE_PRIVATE)
        userLogout()
    }

    private fun userLogout() {
        binding.btnLogout.setOnClickListener {
            val logoutDialog = AlertDialog.Builder(requireContext())
            logoutDialog.setTitle("Logout")
            logoutDialog.setMessage("Apakah anda yakin ingin logout?")
            logoutDialog.setPositiveButton("Logout") {p0, _ ->
                p0.dismiss()
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            logoutDialog.setNegativeButton("Batal") {p0, _ ->
                p0.dismiss()
            }
            logoutDialog.show()
        }
    }

}