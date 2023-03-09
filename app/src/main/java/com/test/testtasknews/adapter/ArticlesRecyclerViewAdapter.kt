package com.test.testtasknews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.testtasknews.R
import com.test.testtasknews.data.remote.model.Article
import com.test.testtasknews.util.ArticleDiffUtil

class ArticlesRecyclerViewAdapter(
    private var articlesList: List<Article>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onFavouriteCheckboxClick: ((Article, Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.article_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        loadImage(articlesList[position].urlToImage, (holder as ViewHolder).imageViewArticleImage)
        holder.textViewArticleTitle.text = articlesList[position].title
        holder.textViewArticleAuthor.text = articlesList[position].author
    }

    private fun loadImage(urlPhoto: String?, imageView: ImageView) {
        Picasso.get().load(urlPhoto).fit().centerCrop()
            .placeholder(R.drawable.blur_placeholder)
            .into(imageView)
    }

    fun updateItems(newArticleList: List<Article>) {
        val articles = ArrayList(articlesList)
        articles.addAll(newArticleList)
        val callback = ArticleDiffUtil(articlesList, articles)
        val result = DiffUtil.calculateDiff(callback)
        articlesList = articles
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = articlesList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageViewArticleImage: ImageView = itemView.findViewById(R.id.image_view_article_image)
        val textViewArticleTitle: TextView = itemView.findViewById(R.id.text_view_article_title)
        val textViewArticleAuthor: TextView = itemView.findViewById(R.id.text_view_article_author)
        private val checkBoxFavourite: CheckBox = itemView.findViewById(R.id.checkbox_favorites)

        init {
            checkBoxFavourite.setOnCheckedChangeListener { _, isChecked ->
                onFavouriteCheckboxClick?.invoke(
                    articlesList[adapterPosition],
                    isChecked
                )
            }
        }

    }
}
