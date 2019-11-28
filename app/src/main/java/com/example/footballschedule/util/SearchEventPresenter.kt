package com.example.footballschedule.util


import com.example.footballschedule.api.ApiRepository
import com.example.footballschedule.model.ApiResponse
import com.example.footballschedule.searchevent.SearchView
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