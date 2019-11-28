package com.example.footballschedule.util

import com.example.footballschedule.league.League
import com.example.footballschedule.model.MatchDetail

interface LeagueView {
    fun showLoading()
    fun hideloading()
    fun showList(data:List<League>)
}