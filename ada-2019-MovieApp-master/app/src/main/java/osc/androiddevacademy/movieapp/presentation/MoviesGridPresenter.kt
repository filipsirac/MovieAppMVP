package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.App
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.MoviesResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.ui.fragments.grid.MoviesGridContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesGridPresenter(private val apiInteractor: MovieInteractor) :
    MoviesGridContract.Presenter {

    private lateinit var view: MoviesGridContract.View

    private val appDatabase by lazy { MoviesDatabase.getInstance(App.getAppContext()) }

    override fun setView(view: MoviesGridContract.View) {
        this.view = view
    }

    override fun requestPopularMovies() {
        apiInteractor.getPopularMovies(popularMoviesCallback())
    }

    private fun popularMoviesCallback(): Callback<MoviesResponse> =
        object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                view.onFailure()
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.movies?.run {
                        view.onSuccessful(this)
                    }
                }
            }
        }

    override fun clickedFavourite(movie: Movie) {
        if (appDatabase.moviesDao().getFavoriteMovies().findLast { it.id == movie.id } == null) {
            appDatabase.moviesDao().addFavoriteMovie(movie)
        } else {
            appDatabase.moviesDao().deleteFavoriteMovie(movie)
        }
    }

    override fun requestFavoriteMovies(): ArrayList<Movie> =
        appDatabase.moviesDao().getFavoriteMovies() as ArrayList<Movie>

}