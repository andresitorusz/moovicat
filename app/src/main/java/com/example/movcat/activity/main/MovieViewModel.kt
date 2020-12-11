package com.example.movcat.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movcat.data.repository.MoviePagedListRepository
import com.example.movcat.data.repository.NetworkState
import com.example.movcat.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val moviePagedListRepository: MoviePagedListRepository)
    : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        moviePagedListRepository.getNetworkState()
    }

    fun isEmpty() : Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}