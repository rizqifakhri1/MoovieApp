package com.rizqi.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.myapplication.di.Repository
import com.rizqi.myapplication.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val  _login : MutableLiveData<UserEntity> = MutableLiveData()
    val login : LiveData<UserEntity> get() = _login

    fun getUser(username: String, password: String) {
        viewModelScope.launch {
            _login.value = repository.getUser(username, password)
        }
    }
}