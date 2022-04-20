package com.rizqi.myapplication.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.rizqi.myapplication.model.GetDetail
import com.rizqi.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val dataDetail : MutableLiveData<GetDetail> = MutableLiveData()
    fun getDetail(id : Int){
        ApiClient.instance.getDetail(id)
            .enqueue(object : Callback<GetDetail> {

                override fun onResponse(
                    call: Call<GetDetail>,
                    response: Response<GetDetail>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        dataDetail.postValue(body)
                    }



                }
                override fun onFailure(call: Call<GetDetail>, t: Throwable) {

                }
            })
    }
}