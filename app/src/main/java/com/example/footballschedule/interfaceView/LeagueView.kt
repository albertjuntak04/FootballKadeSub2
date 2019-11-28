package com.example.footballschedule.interfaceView

import com.example.footballschedule.league.League
import com.example.footballschedule.model.MatchDetail

interface LeagueView {
    fun showLoading()
    fun hideLoading()
    fun showList(data:List<League>)
}