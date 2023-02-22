package com.group3.pocketwiki.holders

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.group3.pocketwiki.R
import com.group3.pocketwiki.activities.ArticleDetailActivity
import com.group3.pocketwiki.models.WikiPage
import com.squareup.picasso.Picasso


class ListItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    private val articleImageView: ImageView = itemView.findViewById<ImageView>(R.id.result_icon)
    private val titleTextView: TextView = itemView.findViewById<TextView>(R.id.result_title)
    private var currentPage: WikiPage? = null

    init {
        itemView.setOnClickListener {
            var detailedPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            var pageJson = Gson().toJson(currentPage)
            detailedPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(detailedPageIntent)
        }
    }

        fun updateWithPage(page: WikiPage) {
            if (page.thumbnail != null)
                Picasso.get().load(page.thumbnail!!.source).into(articleImageView)

            titleTextView.text = page.title

            currentPage = page
        }
    }
