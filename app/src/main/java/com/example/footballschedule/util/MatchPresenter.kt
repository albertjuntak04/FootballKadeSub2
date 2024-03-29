package com.example.footballschedule.util


import com.example.footballschedule.NextMatchFragment
import com.example.footballschedule.api.ApiRepository
import com.example.footballschedule.model.ApiResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(private val view: NextMatchFragment,
                     private val api: String,
                     private val gson: Gson
                     ) {


    fun getList(){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(ApiRepository().doRequest(api), ApiResponse::class.java)

            uiThread {
                view.hideLoading()
                try {
                    view.showList(data.events!!)
                } catch (e: NullPointerException){
                    view.showEmptyData()
                }

            }
        }
    }
}