package com.rizqi.myapplication.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.rizqi.myapplication.R
import com.rizqi.myapplication.databinding.FragmentProfileBinding
import com.rizqi.myapplication.fragments.LoginFragment.Companion.LOGINUSER
import com.rizqi.myapplication.fragments.LoginFragment.Companion.PASSWORD
import com.rizqi.myapplication.fragments.LoginFragment.Companion.USERNAME
import com.rizqi.myapplication.model.UserEntity
import com.rizqi.myapplication.orm.UserDatabase
import com.rizqi.myapplication.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import java.io.File


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var profileViewModel : ProfileViewModel
    private var database : UserDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    private var imageUri: Uri? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    imageUri = data?.data
                    loadImage(imageUri)

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.ivHome.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeScreenFragment)
        }

        binding.ivMiddle.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_movieFragment)
        }

        binding.ivIcon.setOnClickListener {
            openImagePicker()
        }

        database = UserDatabase.getInstance(requireContext())
        sharedPreferences = requireContext().getSharedPreferences(LOGINUSER, Context.MODE_PRIVATE)
        profileViewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        val username = sharedPreferences.getString(USERNAME, "default_username")
        val password = sharedPreferences.getString(PASSWORD, "default_password")
        if (username != null && password != null) {
                getData(username, password)
        }
        var id = 0
        profileViewModel.userData.observe(viewLifecycleOwner){
            id = it.id!!
            binding.tiUserNameEditText.setText(it.username)
            binding.tiUserEmailEditText.setText(it.email)
            binding.tiUserPasswordEditText.setText(it.password)
            binding.ivIcon.setImageURI( it.uri.toString().toUri())
        }
        binding.btnUpdate.setOnClickListener {
            when {
                binding.tiUserNameEditText.text.isNullOrEmpty() -> {
                    binding.tiUserNameLayout.error = "Username belum diisi"
                }
                binding.tiUserPasswordEditText.text.isNullOrEmpty() -> {
                    binding.tiUserPasswordLayout.error = "Password belum diisi"
                }
                binding.tiUserEmailEditText.text.isNullOrEmpty() -> {
                    binding.tiUserPasswordLayout.error = "Email belum diisi"
                } else -> {
                val data = UserEntity(
                    id,
                    binding.tiUserNameEditText.text.toString(),
                    binding.tiUserEmailEditText.text.toString(),
                    binding.tiUserPasswordEditText.text.toString(),
                    imageUri.toString()
                )
                lifecycleScope.launch(Dispatchers.IO){
                    val result = database?.userDao()?.updateUser(data)
                    runBlocking(Dispatchers.Main) {
                        if (result != 0){
                            val editor = sharedPreferences.edit()
                            editor.putString(USERNAME, data.username)
                            editor.apply()
                            Toast.makeText(requireContext(), "Data Berhasil Disimpan ${id}", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_profileFragment_to_homeScreenFragment)
                        } else {
                            Toast.makeText(requireContext(), """
                                    ${id},
                        ${binding.tiUserNameEditText.text},
                        ${binding.tiUserEmailEditText.text},
                        ${binding.tiUserPasswordEditText.text}
                                """.trimIndent(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            }
        }

        userLogout()

    }

    private fun getData(username: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = database?.userDao()?.getUser(username, password)
            runBlocking(Dispatchers.Main) {
                if (result != null) {
                    profileViewModel.dataUser(result)
                }
            }
        }
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

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun loadImage(uri: Uri?) {
        uri?.let {
            binding.ivIcon.setImageURI(it)
        }
    }

}