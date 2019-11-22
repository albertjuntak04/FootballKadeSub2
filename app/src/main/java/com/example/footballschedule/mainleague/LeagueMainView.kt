package com.example.footballschedule.mainleague

import com.example.footballschedule.league.League

interface LeagueMainView {
    fun showLeague(data: List<League>)
    fun showLoading()
    fun showHiding()
}