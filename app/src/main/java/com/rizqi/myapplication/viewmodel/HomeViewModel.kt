package com.rizqi.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.myapplication.di.Repository
import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.GetAllMovieUpcoming
import com.rizqi.myapplication.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val  _getDataUser : MutableLiveData<UserEntity> = MutableLiveData()
    val getDataUser : LiveData<UserEntity> get() = _getDataUser

    private val _userDataStore: MutableLiveData<UserEntity> = MutableLiveData()
    val userDataStore: LiveData<UserEntity> get() = _userDataStore

    private val _popularMovie: MutableLiveData<Resource<GetAllMoviePopular>> = MutableLiveData()
    val popularMovie: LiveData<Resource<GetAllMoviePopular>> get() = _popularMovie

    private val _upcomingMovie: MutableLiveData<Resource<GetAllMovieUpcoming>> = MutableLiveData()
    val upcomingMovie: LiveData<Resource<GetAllMovieUpcoming>> get() = _upcomingMovie


    fun getUser(username: String, password: String) {
        viewModelScope.launch {
            _getDataUser.value = repository.getUser(username, password)
        }
    }

    fun getUpcomingMovie() {
        viewModelScope.launch {
            _upcomingMovie.postValue(Resource.loading())
            try {
                _upcomingMovie.postValue(Resource.success(repository.getUpcomingMovies()))
            }catch (exception:Exception){
                _upcomingMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
    }

    fun getPopularMovie() {
        viewModelScope.launch {
            _popularMovie.postValue(Resource.loading())
            try {
                _popularMovie.postValue(Resource.success(repository.getPopularMovies()))
            }catch (exception:Exception){
                _popularMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
    }
}