package osc.androiddevacademy.movieapp.ui.fragments.pager

interface MoviesPagerContract {

    interface View{
        fun onSuccessfull()
        fun onFailure()
    }

    interface Presenter{
        fun setView(view: View)
    }
}