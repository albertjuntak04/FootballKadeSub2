package com.example.footballschedule.searchevent

import com.example.footballapi.api.ApiRepository
import com.example.footballapi.model.ApiResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchEventPresenter(private val view: SearchView,
                           private val api: String,
                           private val gson: Gson) {
    fun getSearchTeamList(){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(ApiRepository().doRequest(api), ApiResponse::class.java)

            uiThread {
                view.hideLoading()
                try {
                    view.showTeamList(data.event!!)
                }catch (e: NullPointerException){
                    view.showEmptyData()
                }
            }
        }
    }
}