package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.ReviewsResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.ui.fragments.details.ReviewAdapter
import osc.androiddevacademy.movieapp.ui.fragments.details.MovieDetailsContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsPresenter(private val interactor: MovieInteractor):
    MovieDetailsContract.Presenter {

    private lateinit var view: MovieDetailsContract.View

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun onGetReviews(movie: Movie) {
        interactor.getReviewsForMovie(movie.id, reviewsCallback() )
    }

    private fun reviewsCallback(): Callback<ReviewsResponse> = object : Callback<ReviewsResponse> {
        override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
            t.printStackTrace()
            handleSomethingWentWrong()
        }

        override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
            if (response.isSuccessful){
                handleOkResponse(response)
            }
        }
    }

    private fun handleOkResponse(response: Response<ReviewsResponse>) {
        response.body()?.reviews?.run {
            view.onReviewsGetSuccessful(this)
        }
    }

    private fun handleSomethingWentWrong() {
        view.onReviewsGetFailure()
    }


}