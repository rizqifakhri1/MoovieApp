package com.rizqi.myapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rizqi.myapplication.R
import com.rizqi.myapplication.databinding.FragmentLoginBinding
import com.rizqi.myapplication.orm.UserDatabase
import com.rizqi.myapplication.viewmodel.HomeViewModel
import com.rizqi.myapplication.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var mDb : UserDatabase? = null
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val LOGINUSER = "login_username"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = UserDatabase.getInstance(requireContext())


        //Implementasi SharedPreferences
        val preferences = requireContext().getSharedPreferences(LOGINUSER, Context.MODE_PRIVATE)

        val username = preferences.getString(USERNAME, "default_username")
        if (username != "default_username") {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }

        //binding
        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener{
            when {
                binding.tiUserNameEditText.text.isNullOrEmpty() -> {
                    binding.tiUserNameLayout.error = "Username belum diisi"
                }
                binding.tiUserPasswordEditText.text.isNullOrEmpty() -> {
                    binding.tiUserPasswordLayout.error = "Password belum diisi"
                } else -> {
                val username = binding.tiUserNameEditText
                val password = binding.tiUserPasswordEditText
                loginViewModel.getUser(username.toString(), password.toString())
                loginViewModel.login.observe(viewLifecycleOwner) {
                    if (it == null) {
                        val snackbar = Snackbar.make(
                            binding.root,
                            "Login gagal, coba periksa email atau password anda",
                            Snackbar.LENGTH_INDEFINITE
                        )
                        snackbar.setAction("Oke") {
                            snackbar.dismiss()
                        }
                        snackbar.show()
                    } else {
                        val editor : SharedPreferences.Editor = preferences.edit()
                        editor.putString(USERNAME, binding.tiUserNameEditText.text.toString())
                        editor.putString(PASSWORD, binding.tiUserPasswordEditText.text.toString())
                        editor.apply()
                        Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    }
                }
            }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


