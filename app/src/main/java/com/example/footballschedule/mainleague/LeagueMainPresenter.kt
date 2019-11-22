package com.example.footballschedule.mainleague

import com.example.footballapi.api.ApiRepository
import com.example.footballapi.model.ApiResponse
import com.example.footballschedule.NextMatchFragment
import com.example.footballschedule.activity.MainActivity
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeagueMainPresenter(private val view: MainActivity,
                          private val api: String,
                          private val gson: Gson) {
    fun getList(){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(ApiRepository().doRequest(api), LeagueResponse::class.java)

            uiThread {
//                view.showHiding()
                try {
                    view.showLeague(data.countrys!!)
                } catch (e: NullPointerException){
                    view.showHiding()
                }
            }
        }
    }
}

