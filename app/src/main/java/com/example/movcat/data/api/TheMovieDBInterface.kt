package com.example.movcat.data.api

import com.example.movcat.data.vo.MovieDetails
import com.example.movcat.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    //https://api.themoviedb.org/3/movie/now_playing?api_key=6a2b8629fcc338600751630494627b3e&page=1
    //https://api.themoviedb.org/3/movie/upcoming?api_key=6a2b8629fcc338600751630494627b3e&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/299534?api_key=6a2b8629fcc338600751630494627b3e
    //https://api.themoviedb.org/3/

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("page") page: Int) : Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("page") page: Int) : Single<MovieResponse>
}