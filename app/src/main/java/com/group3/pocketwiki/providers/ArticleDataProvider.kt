package com.group3.pocketwiki.providers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.group3.pocketwiki.models.Urls
import com.group3.pocketwiki.models.WikiResult
import java.io.Reader
import java.lang.Exception


class ArticleDataProvider {

    //set user=agent header
    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Group3 PocketWiki")
    }

    fun search(term: String, skip: Int, take: Int, responseHandler:(result: WikiResult) -> Unit? ) {
        Urls.getSearchUrl(term, skip, take).httpGet().
            responseObject(WikipediaDataDeserializer()) { _, response, result ->
                //do something with result
                if(response.statusCode!=200){
                    throw Exception("Unable to get articles")
                }
                val(data, _) = result
                responseHandler.invoke(data as WikiResult)
        }
    }

    fun getRandom(
        take: Int,
        responseHandler: (result: WikiResult) -> Unit?

    ){
        Urls.getRandomUrl(take).httpGet().
            responseObject(WikipediaDataDeserializer()){_, response, result ->
                if(response.statusCode!=200){
                    throw Exception("Unable to get articles")
                }
                val(data) = result
                responseHandler.invoke(data as WikiResult)


        }
    }

    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)

    }
}