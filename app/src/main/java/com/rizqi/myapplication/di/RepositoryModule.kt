package com.rizqi.myapplication.di

import com.rizqi.myapplication.orm.DbHelper
import com.rizqi.myapplication.service.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideRepository(
        apiHelper: ApiHelper,
        dbHelper: DbHelper
    ) = Repository(apiHelper,dbHelper)
}