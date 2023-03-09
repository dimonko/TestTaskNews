package com.test.testtasknews.viewstate

import com.test.testtasknews.data.remote.model.Article

sealed class FavouritesActivityViewState {

    object Loading : FavouritesActivityViewState()
    class Error(val errorText: String?) : FavouritesActivityViewState()
    class Loaded(val favouriteArticleList: List<Article>) : FavouritesActivityViewState()

}