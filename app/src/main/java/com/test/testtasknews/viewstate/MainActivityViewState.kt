package com.test.testtasknews.viewstate

import com.test.testtasknews.data.remote.model.Article

sealed class MainActivityViewState {

    object Loading : MainActivityViewState()
    object LoadingNewArticle : MainActivityViewState()
    class LoadedArticle(val articleList: List<Article>) : MainActivityViewState()
    class LoadedNewArticle(val newArticleList: List<Article>) : MainActivityViewState()
    class Error(val errorText: String?) : MainActivityViewState()

}