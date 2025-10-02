package com.droid.loginsignup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.droid.loginsignup.api.RetrofitInstance
import com.droid.loginsignup.models.UnsplashPhoto
import com.droid.loginsignup.paging.UnsplashPagingSource
import kotlinx.coroutines.flow.Flow

class UnsplashViewModel : ViewModel() {

    private val defaultImagesFlow: Flow<PagingData<UnsplashPhoto>> by lazy {
        createPager("random").cachedIn(viewModelScope) // Cache only default images
    }

    fun getDefaultImages(): Flow<PagingData<UnsplashPhoto>> = defaultImagesFlow

    fun searchImages(query: String): Flow<PagingData<UnsplashPhoto>> {
        return createPager(query).cachedIn(viewModelScope) // Avoid reusing old search results
    }

    private fun createPager(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(PagingConfig(pageSize = 20)) {
            UnsplashPagingSource(RetrofitInstance.api, query)
        }.flow
    }
}
