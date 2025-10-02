package com.droid.loginsignup.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.droid.loginsignup.api.UnsplashApi
import com.droid.loginsignup.models.UnsplashPhoto

class UnsplashPagingSource(private val api: UnsplashApi, private val query: String) : PagingSource<Int, UnsplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        return try {
            val page = params.key ?: 1
            val response = if (query == "random") api.getRandomPhotos() else api.searchPhotos(query, page).results
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}