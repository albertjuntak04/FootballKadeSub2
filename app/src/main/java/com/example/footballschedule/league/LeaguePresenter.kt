package com.example.footballschedule.league


import com.example.footballschedule.activity.DetailLeagueActivity
import com.example.footballschedule.api.ApiRepository
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeaguePresenter(private val view: DetailLeagueActivity,
                      private val api: String,
                      private val gson: Gson
) {

    fun getList(){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(ApiRepository().doRequest(api), LeagueResponse::class.java)

            try {
                uiThread {
                    view.showList(data.leagues[0])
                }
            }catch (e : NullPointerException){
                view.showEmptyData()

            }
    }
 }
}