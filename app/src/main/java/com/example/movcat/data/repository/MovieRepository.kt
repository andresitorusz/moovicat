package com.example.movcat.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movcat.data.api.POST_PER_PAGE
import com.example.movcat.data.api.TheMovieDBInterface
import com.example.movcat.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieRepository (private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList : LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable)
        : LiveData<PagedList<Movie>> {

        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config)
            .build()

        return moviePagedList
    }

    fun getNetworkState() : LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }
}