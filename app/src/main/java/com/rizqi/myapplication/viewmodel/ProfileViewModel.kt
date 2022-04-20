package com.rizqi.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizqi.myapplication.model.GetDetail
import com.rizqi.myapplication.model.UserEntity

class ProfileViewModel : ViewModel() {
    val userData : MutableLiveData<UserEntity> = MutableLiveData()
    fun dataUser(userEntity: UserEntity){
        userData.postValue(userEntity)
    }
}