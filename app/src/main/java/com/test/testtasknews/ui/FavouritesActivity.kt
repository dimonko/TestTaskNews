package com.test.testtasknews.ui

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testtasknews.R
import com.test.testtasknews.adapter.FavouriteArticlesRecyclerViewAdapter
import com.test.testtasknews.adapter.ItemDecorator
import com.test.testtasknews.data.remote.model.Article
import com.test.testtasknews.databinding.FavouritesActivityBinding
import com.test.testtasknews.ui.base.ActivityEx
import com.test.testtasknews.ui.dialog.LoadingDialog
import com.test.testtasknews.viewmodel.FavouritesActivityViewModel
import com.test.testtasknews.viewstate.FavouritesActivityViewState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesActivity : ActivityEx() {

    private val binding: FavouritesActivityBinding by lazy {
        FavouritesActivityBinding.inflate(
            layoutInflater
        )
    }
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this) }
    private val favouriteActivityViewModel: FavouritesActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureViewElement()
        subscribeViewModel()
        favouriteActivityViewModel.loadFavouritesArticles()
    }

    private fun configureViewElement() {
        binding.imageViewBack.setOnClickListener { finish() }
    }

    private fun subscribeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                favouriteActivityViewModel.favouritesActivityScreenState.collect { state ->
                    when (state) {
                        is FavouritesActivityViewState.Error -> errorState(state.errorText)
                        is FavouritesActivityViewState.Loaded -> loadedState(state.favouriteArticleList)
                        is FavouritesActivityViewState.Loading -> loadingState()
                    }
                }
            }
        }
    }

    private fun errorState(errorText: String?) {
        loadingDialog.dismiss()
        showSnackBarError(errorText ?: getString(R.string.default_error_text))
    }

    private fun loadedState(favouriteArticleList: List<Article>) {
        loadingDialog.dismiss()
        setAdapter(favouriteArticleList)
    }

    private fun loadingState() {
        loadingDialog.show()
    }

    private fun setAdapter(favouriteArticleList: List<Article>) {
        val articlesRecyclerViewAdapter = FavouriteArticlesRecyclerViewAdapter(favouriteArticleList)
        binding.recyclerViewFavourites.apply {
            val linearLayoutManager = LinearLayoutManager(
                this@FavouritesActivity,
                LinearLayoutManager.VERTICAL, false
            )
            layoutManager = linearLayoutManager
            adapter = articlesRecyclerViewAdapter
            addItemDecoration(ItemDecorator(15, 15, 10, 10))
        }
    }
}