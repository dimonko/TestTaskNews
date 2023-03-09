package com.test.testtasknews.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testtasknews.R
import com.test.testtasknews.adapter.ArticlesRecyclerViewAdapter
import com.test.testtasknews.adapter.PaginationScrollListener
import com.test.testtasknews.data.remote.model.Article
import com.test.testtasknews.databinding.ActivityMainBinding
import com.test.testtasknews.ui.base.ActivityEx
import com.test.testtasknews.ui.dialog.LoadingDialog
import com.test.testtasknews.viewmodel.MainActivityViewModel
import com.test.testtasknews.viewstate.MainActivityViewState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ActivityEx() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this) }
    private lateinit var articlesRecyclerViewAdapter: ArticlesRecyclerViewAdapter
    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureViewElement()
        subscribeViewModel()
        mainActivityViewModel.loadArticles()
    }

    private fun subscribeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mainActivityViewModel.mainActivityScreenState.collect { state ->
                    when (state) {
                        is MainActivityViewState.Loading -> loadingState()
                        is MainActivityViewState.LoadingNewArticle -> binding.progressBarLoadNextArticle.visibility =
                            View.VISIBLE
                        is MainActivityViewState.LoadedArticle -> loadedState(state.articleList)
                        is MainActivityViewState.LoadedNewArticle -> loadedNewArticle(state.newArticleList)
                        is MainActivityViewState.Error -> errorState(state.errorText)
                    }
                }
            }
        }
    }

    private fun configureViewElement() {
        binding.buttonFavourites.setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
        binding.buttonSortPopularity.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) mainActivityViewModel.loadArticlesWithPopularitySorting()
            else mainActivityViewModel.loadArticles()
        }
    }

    private fun loadedState(articleList: List<Article>) {
        loadingDialog.dismiss()
        setAdapter(articleList)
    }

    private fun loadedNewArticle(newArticleList: List<Article>) {
        articlesRecyclerViewAdapter.updateItems(newArticleList)
        isLoading = false
        binding.progressBarLoadNextArticle.visibility = View.GONE
        loadingDialog.dismiss()
    }

    private fun setAdapter(articleList: List<Article>) {
        articlesRecyclerViewAdapter = ArticlesRecyclerViewAdapter(articleList)
        binding.recyclerViewNews.apply {
            val linearLayoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.VERTICAL, false
            )
            layoutManager = linearLayoutManager
            adapter = articlesRecyclerViewAdapter
            val isLastPage = false
            addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override fun isLastPage(): Boolean = isLastPage
                override fun isLoading(): Boolean = isLoading
                override fun loadMoreItems() {
                    isLoading = true
                    if (!binding.buttonSortPopularity.isChecked) mainActivityViewModel.loadNewArticles()
                    else mainActivityViewModel.loadNewArticlesWithPopularitySorting()
                }
            })
            articlesRecyclerViewAdapter.onFavouriteCheckboxClick = { article, isFavourite ->
                if (isFavourite) mainActivityViewModel.saveArticleToDatabase(article)
                else mainActivityViewModel.deleteArticleFromDatabase(article)
            }
        }
    }

    private fun loadingState() = loadingDialog.show()

    private fun errorState(errorText: String?) {
        loadingDialog.dismiss()
        binding.progressBarLoadNextArticle.visibility = View.GONE
        showSnackBarError(errorText ?: getString(R.string.default_error_text))
    }

}