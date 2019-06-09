package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.ui.fragments.pager.MoviesPagerContract

class MoviesPagerPresenter: MoviesPagerContract.Presenter {

    private lateinit var view: MoviesPagerContract.View

    override fun setView(view: MoviesPagerContract.View) {
        this.view = view
    }



}