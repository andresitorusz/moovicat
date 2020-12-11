package com.example.movcat.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movcat.R
import com.example.movcat.data.api.TheMovieDBClient
import com.example.movcat.data.api.TheMovieDBInterface
import com.example.movcat.data.repository.MoviePagedListAdapter
import com.example.movcat.data.repository.MoviePagedListRepository
import com.example.movcat.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter = MoviePagedListAdapter(this)

        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        rv_movie_now_playing.layoutManager = layoutManager
        rv_movie_now_playing.setHasFixedSize(true)
        rv_movie_now_playing.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_now_playing.visibility = if (viewModel.isEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            error_text_now_playing.visibility = if (viewModel.isEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.isEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(
                    movieRepository
                ) as T
            }
        })[MovieViewModel::class.java]
    }
}