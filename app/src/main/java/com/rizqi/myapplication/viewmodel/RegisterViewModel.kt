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
class RegisterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val  _register : MutableLiveData<Long> = MutableLiveData()
    val register : LiveData<Long> get() = _register

    fun addUser(user: UserEntity){
        viewModelScope.launch {
            _register.value = repository.addUser(user)
        }
    }
}