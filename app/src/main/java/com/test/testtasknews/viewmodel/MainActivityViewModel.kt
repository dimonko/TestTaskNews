package com.test.testtasknews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtasknews.data.remote.model.Article
import com.test.testtasknews.data.repository.NewsRepository
import com.test.testtasknews.data.room.RoomArticleDao
import com.test.testtasknews.extension.launchIO
import com.test.testtasknews.viewstate.MainActivityViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel constructor(
    private val newsRepository: NewsRepository, private val articleDao: RoomArticleDao
) : ViewModel() {

    private val _mainActivityScreenState =
        MutableStateFlow<MainActivityViewState>(MainActivityViewState.Loading)
    val mainActivityScreenState: StateFlow<MainActivityViewState>
        get() = _mainActivityScreenState
    private var pageDefault = 1
    private var pagePopularity = 1

    fun loadArticles() {
        pageDefault = 1
        viewModelScope.launchIO(safeAction = {
            _mainActivityScreenState.value = MainActivityViewState.Loading
            val articles = newsRepository.getArticles(page = pageDefault)
            _mainActivityScreenState.value = MainActivityViewState.LoadedArticle(articles)
            pageDefault++
        }, onError = {
            _mainActivityScreenState.value = MainActivityViewState.Error(it.localizedMessage)
        })
    }

    fun loadNewArticles() {
        viewModelScope.launchIO(safeAction = {
            _mainActivityScreenState.value = MainActivityViewState.LoadingNewArticle
            val articles = newsRepository.getArticles(page = pageDefault)
            _mainActivityScreenState.value = MainActivityViewState.LoadedNewArticle(articles)
            pageDefault++
        }, onError = {
            _mainActivityScreenState.value = MainActivityViewState.Error(it.localizedMessage)
        })
    }

    fun loadArticlesWithPopularitySorting() {
        pagePopularity = 1
        viewModelScope.launchIO(safeAction = {
            _mainActivityScreenState.value = MainActivityViewState.Loading
            val articles = newsRepository.getArticlesWithPopularitySorting(page = pagePopularity)
            _mainActivityScreenState.value = MainActivityViewState.LoadedArticle(articles)
            pagePopularity++
        }, onError = {
            _mainActivityScreenState.value = MainActivityViewState.Error(it.localizedMessage)
        })
    }

    fun loadNewArticlesWithPopularitySorting() {
        viewModelScope.launchIO(safeAction = {
            _mainActivityScreenState.value = MainActivityViewState.LoadingNewArticle
            val articles = newsRepository.getArticlesWithPopularitySorting(page = pagePopularity)
            _mainActivityScreenState.value = MainActivityViewState.LoadedNewArticle(articles)
            pagePopularity++
        }, onError = {
            _mainActivityScreenState.value = MainActivityViewState.Error(it.localizedMessage)
        })
    }

    fun saveArticleToDatabase(article: Article) {
        viewModelScope.launchIO(safeAction = {
            articleDao.saveArticleToDatabase(article)
        }, onError = {
            _mainActivityScreenState.value = MainActivityViewState.Error(it.localizedMessage)
        })
    }

    fun deleteArticleFromDatabase(article: Article) {
        viewModelScope.launchIO(safeAction = {
            articleDao.deleteArticleFromDatabase(article)
        }, onError = {
            _mainActivityScreenState.value = MainActivityViewState.Error(it.localizedMessage)
        })
    }

}