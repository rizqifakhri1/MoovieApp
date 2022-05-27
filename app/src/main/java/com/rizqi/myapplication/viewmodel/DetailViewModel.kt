package com.rizqi.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.myapplication.di.Repository
import com.rizqi.myapplication.model.GetDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
//    val detailMovie: MutableLiveData<DetailMovieResponse> = MutableLiveData()

    private val _detailMovie: MutableLiveData<Resource<GetDetail>> = MutableLiveData()
    val detailMovie: LiveData<Resource<GetDetail>> get() = _detailMovie

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getMovieById(id)))
            }catch (exception:Exception){
                _detailMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
    }
}