package com.rizqi.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.myapplication.di.Repository
import com.rizqi.myapplication.model.GetDetail
import com.rizqi.myapplication.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _update : MutableLiveData<Int> = MutableLiveData()
    val update : LiveData<Int> get() = _update

    fun updateUser(user: UserEntity){
        viewModelScope.launch {
            _update.value = repository.updateUser(user)
        }
    }
}