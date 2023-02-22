package com.group3.pocketwiki

import android.app.Application
import com.group3.pocketwiki.managers.WikiManager
import com.group3.pocketwiki.providers.ArticleDataProvider
import com.group3.pocketwiki.repositories.ArticleDatabaseOpenHelper
import com.group3.pocketwiki.repositories.FavoritesRepository
import com.group3.pocketwiki.repositories.HistoryRepository

class WikiApplication: Application() {
    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var favoritesRepository: FavoritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider? = null
    var wikiManager: WikiManager? = null
        private set

    override fun onCreate(){
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()

        wikiManager = WikiManager(wikiProvider!!, favoritesRepository!!, historyRepository!!)
    }
}