package osc.androiddevacademy.movieapp.ui.fragments.details

import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.Review

interface MovieDetailsContract {

    interface View{
        fun onReviewsGetSuccessful(list: List<Review>)
        fun onReviewsGetFailure()
    }

    interface Presenter{
        fun setView(view: View)

        fun onGetReviews(movie: Movie)

    }
}