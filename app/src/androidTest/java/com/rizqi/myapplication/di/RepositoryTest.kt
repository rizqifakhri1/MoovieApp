package com.rizqi.myapplication.di

import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.GetAllMovieUpcoming
import com.rizqi.myapplication.model.UserEntity
import com.rizqi.myapplication.orm.DbHelper
import com.rizqi.myapplication.service.ApiHelper
import com.rizqi.myapplication.service.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.internal.tls.OkHostnameVerifier.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class RepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var dbHelper: DbHelper

    //system under test
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        apiService = mockk()
        dbHelper = mockk()
        apiHelper = ApiHelper(apiService)
        repository = Repository(apiHelper, dbHelper)
    }

    @Test
    fun getPopularMovies(): Unit = runBlocking {
        val responseMovies = mockk<GetAllMoviePopular>()

        every {
            runBlocking {
                apiService.getPopularMovie()
            }
        } returns responseMovies

        repository.getPopularMovies()

        verify {
            runBlocking {
                apiService.getPopularMovie()
            }
        }
    }

    @Test
    fun getUpcomingMovies(): Unit = runBlocking {
        val responseMovies = mockk<GetAllMovieUpcoming>()

        every {
            runBlocking {
                apiService.getUpcomingMovie()
            }
        } returns responseMovies

        repository.getUpcomingMovies()

        verify {
            runBlocking {
                apiService.getUpcomingMovie()
            }
        }
    }

    @Test
    fun getUser(): Unit = runBlocking {
        val responseGetUser = mockk<UserEntity>()

        every {
            runBlocking {
                dbHelper.getUser("test", "test")
            }
        } returns responseGetUser

        repository.getUser("test", "test")
        verify {
            runBlocking {
                dbHelper.getUser("test", "test")
            }
        }
    }

}