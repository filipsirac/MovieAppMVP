package osc.androiddevacademy.movieapp.ui.fragments.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragemnt_movie_grid.*
import kotlinx.android.synthetic.main.item_movie.*
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.displayToast
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.networking.BackendFactory
import osc.androiddevacademy.movieapp.presentation.MoviesGridPresenter
import osc.androiddevacademy.movieapp.ui.fragments.pager.MoviesPagerFragment

class MoviesGridFragment : Fragment(), MoviesGridContract.View {

    private val SPAN_COUNT = 2

    private val presenter: MoviesGridContract.Presenter by lazy {
        MoviesGridPresenter(BackendFactory.getMovieInteractor())
    }
    private val movieList = arrayListOf<Movie>()


    private val gridAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragemnt_movie_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavoriteMovies.setOnClickListener { requestFavoriteMovie() }
        moviesGrid.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
        presenter.setView(this)
        requestPopularMovies()
    }

    override fun onResume() {
        super.onResume()
        getFavoriteMovies.setOnClickListener { requestFavoriteMovie() }
        requestPopularMovies()
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(
            R.id.mainFragmentHolder,
            MoviesPagerFragment.getInstance(
                movieList ,
                movie
            ),
            true
        )
    }

    private fun requestFavoriteMovie(){
        val movies = presenter.requestFavoriteMovies()
        movies.forEach { it.isFavorite = true }
        movieList.clear()
        movieList.addAll(movies)
        gridAdapter.setMovies(movieList)
    }

    private fun requestPopularMovies() {
        presenter.requestPopularMovies()
    }

    private fun onFavoriteClicked(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        gridAdapter.notifyDataSetChanged()
        presenter.clickedFavourite(movie)
        requestFavoriteMovie()

    }

    private fun onGetMovieList(movies: ArrayList<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        gridAdapter.setMovies(movieList)
    }

    override fun onSuccessful(movieList: ArrayList<Movie>) {
        onGetMovieList(movieList)
    }

    override fun onFailure() {
        activity?.displayToast("Failed")
    }

}