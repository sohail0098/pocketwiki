package com.group3.pocketwiki.fragments


import androidx.appcompat.app.AlertDialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.group3.pocketwiki.R
import com.group3.pocketwiki.WikiApplication
import com.group3.pocketwiki.activities.SearchActivity
import com.group3.pocketwiki.adapters.ArticleCardRecyclerAdapter
import com.group3.pocketwiki.managers.WikiManager
//import com.group3.pocketwiki.providers.ArticleDataProvider
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null
    var refresher: androidx.swiperefreshlayout.widget.SwipeRefreshLayout? = null
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById<CardView>(R.id.search_card_view)
        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)
        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)

        searchCardView!!.setOnClickListener{
            val searchIntent = Intent(context, SearchActivity::class.java)
            context?.startActivity(searchIntent)
        }

        exploreRecycler!!.layoutManager =
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }

        getRandomArticles()

        return view
    }


    private fun getRandomArticles(){
        refresher?.isRefreshing = true

        try{
            wikiManager?.getRandom(15) { wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query!!.pages)
                activity?.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }
            }
        }
        catch (ex :Exception){
            // show alert
            val builder = activity?.let { AlertDialog.Builder(it) }
            if (builder != null) {
                builder.setMessage(ex.message).setTitle("oops!")
            }
            val dialog = builder?.create()
            if (dialog != null) {
                dialog.show()
            }
        }
    }

}