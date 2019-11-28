package com.example.footballschedule.interfaceView

import com.example.footballschedule.league.League

interface LeagueMainView {
    fun showLeague(data: List<League>)
    fun showLoading()
    fun showHiding()
}