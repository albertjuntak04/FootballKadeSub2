package com.example.footballschedule.util


import com.example.footballschedule.api.ApiRepository
import com.example.footballschedule.api.TheSportTeamApi
import com.example.footballschedule.interfaceView.MainView
import com.example.footballschedule.model.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson
) {

    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportTeamApi.getTeams(league)),
                TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                try {
                    view.showTeamList(data.teams!!)
                } catch (e: NullPointerException){
                    view.showEmptyData()

                }

            }
        }
    }

}