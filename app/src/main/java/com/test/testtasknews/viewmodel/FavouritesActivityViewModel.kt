package com.test.testtasknews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtasknews.data.room.RoomArticleDao
import com.test.testtasknews.extension.launchIO
import com.test.testtasknews.viewstate.FavouritesActivityViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavouritesActivityViewModel constructor(private val articleDao: RoomArticleDao) : ViewModel() {

    private val _favouritesActivityScreenState =
        MutableStateFlow<FavouritesActivityViewState>(FavouritesActivityViewState.Loading)
    val favouritesActivityScreenState: StateFlow<FavouritesActivityViewState>
        get() = _favouritesActivityScreenState

    fun loadFavouritesArticles() {
        viewModelScope.launchIO(safeAction = {
            _favouritesActivityScreenState.value = FavouritesActivityViewState.Loading
            val result = articleDao.getArticlesFromDatabase()
            _favouritesActivityScreenState.value = FavouritesActivityViewState.Loaded(result)
        }, onError = {
            _favouritesActivityScreenState.value = FavouritesActivityViewState.Error(it.localizedMessage)
        })
    }

}