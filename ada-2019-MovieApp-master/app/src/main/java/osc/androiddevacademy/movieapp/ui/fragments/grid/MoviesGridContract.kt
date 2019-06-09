package osc.androiddevacademy.movieapp.ui.fragments.grid

import osc.androiddevacademy.movieapp.model.Movie

interface MoviesGridContract {
    interface View{
        fun onSuccessful(movieList: ArrayList<Movie>)
        fun onFailure()
    }

    interface Presenter{
        fun setView(view: View)

        fun requestPopularMovies()

        fun clickedFavourite(movie: Movie)

        fun requestFavoriteMovies(): ArrayList<Movie>

    }
}