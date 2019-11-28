package com.example.footballschedule.mainleague
import com.example.footballschedule.api.ApiRepository

import com.example.footballschedule.fragment.TeamsFragment
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeagueMainPresenter(private val view: TeamsFragment,
                          private val api: String,
                          private val gson: Gson) {
    fun getList(){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(ApiRepository().doRequest(api), LeagueResponse::class.java)

            uiThread {
                view.showHiding()
                try {
                    view.showLeague(data.countrys)
                } catch (e: NullPointerException){

                }
            }
        }
    }
}

