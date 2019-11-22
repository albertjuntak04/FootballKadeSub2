package com.example.footballschedule.league

import com.example.footballapi.api.ApiRepository
import com.example.footballschedule.activity.DetailLeagueActivity
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
            val data = gson.fromJson(ApiRepository().doRequest(api), com.example.footballschedule.league.LeagueResponse::class.java)

            uiThread {
              view.showList(data.leagues[0])
        }
    }
 }
}